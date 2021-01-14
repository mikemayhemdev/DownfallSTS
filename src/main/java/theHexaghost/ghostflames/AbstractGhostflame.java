package theHexaghost.ghostflames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.actions.GreenFlameAction;
import theHexaghost.util.OnChargeSubscriber;
import downfall.util.TextureLoader;
import theHexaghost.vfx.MyOrb;

import static theHexaghost.GhostflameHelper.activeGhostFlame;

public abstract class AbstractGhostflame {
    public int damage = -1;
    public int block = -1;
    public int magic = -1;

    public boolean charged = false;

    public MyOrb graphicalRender;

    public Hitbox hitbox;
    public Hitbox hitbox2;
    public float lx;
    public float ly;
    public float flashTimer = 1.0F;

    public float effectIconXOffset = 0F;
    public float effectIconYOffset = 0F;

    public int triggersRequired = 0;

    public Color textColor = new Color(1F, 1F, 1F, 1F);

    public float animAlphaBySlot[] = new float[3];
    private boolean useBrightTexture[] = new boolean[3];

    public boolean advanceOnCardUse = false;

    public static final float whiteOverlayTimer = .4F;

    private Texture whiteOverlay = TextureLoader.getTexture(HexaMod.makeUIPath("whiteOverlay.png"));


    public AbstractGhostflame(float x, float y) {
        lx = x;
        ly = y;
        hitbox = new Hitbox(x, y, 80 * Settings.scale, 80 * Settings.scale);
        hitbox2 = new Hitbox(AbstractDungeon.player.drawX - (115F * Settings.scale), AbstractDungeon.player.drawY + (470.0F * Settings.scale), 270 * Settings.scale, 80 * Settings.scale);
        graphicalRender = new MyOrb(x, y, this, hitbox);
    }

    public void advanceTrigger(AbstractCard c) {

    }

    public void advanceTriggerAnim() {
        if (getActiveFlamesTriggerCount() <= 2) {
            animAlphaBySlot[getActiveFlamesTriggerCount()] = AbstractGhostflame.whiteOverlayTimer;
        }
    }

    public int getActiveFlamesTriggerCount() {
        return 0;
    }

    public void charge() {
        if (!charged) {
            flash();
            charged = true;
            atb(new GreenFlameAction(graphicalRender));
            onCharge();
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnChargeSubscriber) ((OnChargeSubscriber) p).onCharge(this);
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof OnChargeSubscriber) ((OnChargeSubscriber) r).onCharge(this);
            }
            if (AbstractDungeon.player instanceof TheHexaghost) {
                int x = 0;
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames)
                    if (gf.charged) x++;
                ((TheHexaghost) AbstractDungeon.player).myBody.targetRotationSpeed = 100F + (20 * x);
            }
            for (int i = 0; i < 3; i++) {
                if (animAlphaBySlot[i] <= 0F && !useBrightTexture[i]) {
                    animAlphaBySlot[i] = AbstractGhostflame.whiteOverlayTimer;
                }
            }
            reset();
        }
    }

    public abstract void onCharge();

    public void update() {
        graphicalRender.update();
        hitbox.update();
        hitbox2.update();
        if (flashTimer > 1.0F)
            flashTimer -= Gdx.graphics.getDeltaTime();
        for (int i = 0; i < 3; i++) {
            if (animAlphaBySlot[i] > 0F) {
                animAlphaBySlot[i] -= Gdx.graphics.getDeltaTime();
                if (animAlphaBySlot[i] < whiteOverlayTimer / 2F) {
                    useBrightTexture[i] = true;
                }
                if (animAlphaBySlot[i] < 0F) {
                    animAlphaBySlot[i] = 0F;
                }
            }
        }
        if (flashTimer < 1.0F)
            flashTimer = 1.0F;
    }

    public abstract Texture getHelperTexture();

    public abstract Texture getHelperTextureBright();

    public abstract Texture getHelperEffectTexture();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String returnHoverHelperText();

    public void atb(AbstractGameAction e) {
        AbstractDungeon.actionManager.addToBottom(e);
    }

    public void att(AbstractGameAction e) {
        AbstractDungeon.actionManager.addToTop(e);
    }

    public void renderGhostflameTriggerUI(SpriteBatch sb) {
        if (activeGhostFlame == this) {

            int slots = this.triggersRequired;

            float allSlotXOffset = GhostflameHelper.globalX + -20F * Settings.scale;

            float triggerSlot1x = AbstractDungeon.player.drawX - (100.0F * Settings.scale) + allSlotXOffset;
            float triggerSlot1y = AbstractDungeon.player.drawY + (480.0F * Settings.scale);

            float triggerSlot2x = AbstractDungeon.player.drawX + allSlotXOffset;
            float triggerSlot2y = AbstractDungeon.player.drawY + (480.0F * Settings.scale);

            float triggerSlot3x = AbstractDungeon.player.drawX + (100.0F * Settings.scale) + allSlotXOffset;
            float triggerSlot3y = AbstractDungeon.player.drawY + (480.0F * Settings.scale);

            for (int i = 0; i < slots; i++) {
                float x = 0F;
                float y = 0F;

                switch (slots) {
                    case 1: {
                        x = triggerSlot2x;
                        y = triggerSlot2y;
                        break;
                    }
                    case 2: {
                        switch (i) {
                            case 0: {
                                x = triggerSlot2x - ((triggerSlot2x - triggerSlot1x) / 2);
                                y = triggerSlot2y;
                                break;
                            }
                            case 1: {
                                x = triggerSlot2x + ((triggerSlot2x - triggerSlot1x) / 2);
                                y = triggerSlot2y;
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        switch (i) {
                            case 0: {
                                x = triggerSlot1x;
                                y = triggerSlot1y;
                                break;
                            }
                            case 1: {
                                x = triggerSlot2x;
                                y = triggerSlot2y;
                                break;
                            }
                            case 2: {
                                x = triggerSlot3x;
                                y = triggerSlot3y;
                                break;
                            }
                        }
                        break;
                    }
                }

                Texture b;
                if (useBrightTexture[i]) {
                    b = getHelperTextureBright();
                } else {
                    b = getHelperTexture();
                }
                sb.setColor(1F, 1F, 1F, .9F);
                sb.draw(b, x, y, 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);

                if (animAlphaBySlot[i] > 0F) {
                    ////SlimeboundMod.logger.info("Anim alpha slot " + i + " animating: " + animAlphaBySlot[i]);
                    if (animAlphaBySlot[i] < whiteOverlayTimer / 2F) {
                        float alpha = Interpolation.linear.apply(0F, .95F, animAlphaBySlot[i] / (whiteOverlayTimer / 2F));
                        sb.setColor(1F, 1F, 1F, alpha);
                    } else {
                        float alpha = Interpolation.linear.apply(.95F, 0F, (animAlphaBySlot[i] - (whiteOverlayTimer / 2F)) / (whiteOverlayTimer / 2F));
                        sb.setColor(1F, 1F, 1F, alpha);
                    }

                    sb.draw(whiteOverlay, x, y, 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);

                }


                sb.setColor(1F, 1F, 1F, 1F);

            }


        }
    }

    public void extinguish() {
        graphicalRender.charged = false;
        charged = false;
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);// 297
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);// 298
        reset();
        if (AbstractDungeon.player instanceof TheHexaghost) {
            int x = 0;
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames)
                if (gf.charged) x++;
            ((TheHexaghost) AbstractDungeon.player).myBody.targetRotationSpeed = 100F + (20 * x);
        }
        for (int i = 0; i < 3; i++) {
            animAlphaBySlot[i] = 0F;
            useBrightTexture[i] = false;
            update();
        }
    }

    public void flash() {
        if (!this.charged) flashTimer = 1.5F;
    }

    public Color getFlameColor() {
        return Color.SKY.cpy();
        //return Color.SKY.cpy();
    }

    public Color getActiveColor() {
        return Color.PURPLE.cpy();
        //return Color.PURPLE.cpy();
    }

    public void reset() {

    }

    public void activate() {
        GhostflameHelper.activeGhostFlame = this;
        for (int i = 0; i < 3; i++) {
            animAlphaBySlot[i] = 0F;
            useBrightTexture[i] = false;
            update();
        }
    }
}
