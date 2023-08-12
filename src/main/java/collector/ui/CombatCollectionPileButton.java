package collector.ui;

import basemod.ClickableUIElement;
import collector.CollectorChar;
import collector.CollectorCollection;
import collector.patches.ExtraDeckButtonPatches.ExhaustPileViewScreenPatches;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static collector.CollectorMod.makeID;

public class CombatCollectionPileButton extends ClickableUIElement {
    private static final float X_OFF = 0f * Settings.scale;
    private static final float Y_OFF = 150f * Settings.scale;
    private static final float HB_WIDTH = 128f;
    private static final float HB_HEIGHT = 128f;
    private static final float COUNT_X = 48.0F * Settings.scale;
    private static final float COUNT_Y = -16.0F * Settings.scale;
    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;
    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;
    private static final float DECK_TIP_X = 0F * Settings.scale;
    private static final float DECK_TIP_Y = 128.0F * Settings.scale;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("CombatCollectionPileButton"));
    public static final String[] TEXT = uiStrings.TEXT;

    private final BobEffect bob;

    private boolean isOpen = false;

    public CombatCollectionPileButton() {
        super((Texture) null,
                0f,
                Y_OFF,
                HB_WIDTH,
                HB_HEIGHT);
        bob = new BobEffect(1.1f);
    }

    @Override
    protected void onHover() {
    }

    @Override
    protected void onUnhover() {
    }

    @Override
    protected void onClick() {
        if (isOpen && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
            isOpen = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
        } else if (!AbstractDungeon.isScreenUp) {
            if (CollectorCollection.combatCollection.isEmpty()) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, TEXT[2], true));
            } else {
                ExhaustPileViewScreenPatches.showCollection = true;
                AbstractDungeon.exhaustPileViewScreen.open();
                isOpen = true;
            }
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x + X_OFF);
    }

    @Override
    public void update() {
        super.update();
        bob.update();
    }

    public float getRenderX() {
        return hitbox.x + hitbox.width / 2F;
    }

    public float getRenderY() {
        return hitbox.y + (hitbox.height / 2F) + bob.y;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (CollectorCollection.combatCollection != null && (AbstractDungeon.player.chosenClass == CollectorChar.Enums.THE_COLLECTOR)) {
            if (!AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
                float x = hitbox.x + hitbox.width / 2f;
                float y = hitbox.y + hitbox.height / 2f;
                sb.setColor(Color.WHITE);
                draw(sb, ImageMaster.DECK_ICON, x, y + bob.y);

                String msg = Integer.toString(CollectorCollection.combatCollection.size());
                sb.setColor(Color.WHITE);
                draw(sb,
                        ImageMaster.DECK_COUNT_CIRCLE,
                        x + COUNT_OFFSET_X,
                        y + COUNT_OFFSET_Y);
                FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, x + COUNT_X, y + COUNT_Y);

                hitbox.render(sb);
                if (hitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
                    TipHelper.renderGenericTip(x + DECK_TIP_X, y + DECK_TIP_Y, TEXT[0], TEXT[1]);
                }
            }
        }
        this.hitbox.render(sb);
    }

    public Vector2 getLocation() {
        return new Vector2(x, y);
    }

    public static void draw(SpriteBatch sb, Texture texture, float cX, float cY) {
        drawScaledAndRotated(sb, texture, cX, cY, 1f, 0f);
    }

    public static void drawScaled(SpriteBatch sb, Texture texture, float cX, float cY, float scale) {
        drawScaledAndRotated(sb, texture, cX, cY, scale, 0f);
    }

    public static void drawRotated(SpriteBatch sb, Texture texture, float cX, float cY, float rotation) {
        drawScaledAndRotated(sb, texture, cX, cY, 1f, rotation);
    }

    public static void drawScaledAndRotated(SpriteBatch sb, Texture texture, float cX, float cY, float scale, float rotation) {
        float w = texture.getWidth();
        float h = texture.getHeight();
        float halfW = w / 2f;
        float halfH = h / 2f;
        sb.draw(texture,
                cX - halfW,
                cY - halfH,
                halfW,
                halfH,
                w,
                h,
                scale * Settings.scale,
                scale * Settings.scale,
                rotation,
                0,
                0,
                (int) w,
                (int) h,
                false,
                false);
    }

}
