package collector.util;

import basemod.abstracts.CustomReward;
import basemod.helpers.CardModifierManager;
import collector.CollectorMod;
import collector.cardmods.CollectedCardMod;
import collector.patches.EssencePatches.TopPanelEssence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.patches.RewardItemTypeEnumPatch;

import static collector.CollectorMod.makeID;

public class CollectibleCardReward extends CustomReward {

    //Thanks Packmaster! AND RORSTS!

    private static final float GOLD_TEXT_X = 1135.0F * Settings.scale;
    private static final float GOLD_IMG_X = GOLD_TEXT_X - 66.0f * Settings.scale;
    private static final float GOLD_IMG_SIZE = (float) ImageMaster.UI_GOLD.getWidth() * Settings.scale;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("CollectibleCardReward"));
    private static final Color TIP_COL = Color.WHITE.cpy();
    private static final float XOFFSET = 25f * Settings.scale;
    protected static final float REWARD_X_POS = Settings.WIDTH * 0.434F;
    public AbstractCard card;
    protected AbstractCard renderCard;

    public CollectibleCardReward(AbstractCard c) {
        super((Texture) null, "", RewardItemTypeEnumPatch.COLLECTOR_COLLECTIBLECARDREWARD);
        card = c;
        CardModifierManager.addModifier(card, new CollectedCardMod());
        init();
    }

    public CollectibleCardReward(String id) {
        super((Texture) null, "", RewardItemTypeEnumPatch.COLLECTOR_COLLECTIBLECARDREWARD);
        card = CardLibrary.getCopy(id, 0, 0);
        CardModifierManager.addModifier(card, new CollectedCardMod());
        init();
    }

    protected void init() {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onPreviewObtainCard(card);
        }
        renderCard = card.makeStatEquivalentCopy();
        text = uiStrings.TEXT[0] + card.name;
        TIP_COL.a = 0.65f;
    }

    @Override
    public boolean claimReward() {
        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(card, InputHelper.mX, InputHelper.mY));
        EssenceSystem.changeEssence(3);
        return true;
    }

    @Override
    public void update() {
        if (hb.hovered && InputHelper.justClickedRight && !isDone) {
            CardCrawlGame.sound.playA("UI_CLICK_1", 0.25f);
            CardCrawlGame.cardPopup.open(card);
        }
        super.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        Color col;
        if (hb.hovered) {
            sb.setColor(new Color(0.4f, 0.6f, 0.6f, 1.0f));
            col = Settings.GOLD_COLOR;
        } else {
            sb.setColor(new Color(0.5f, 0.6f, 0.6f, 0.8f));
            col = Settings.CREAM_COLOR;
        }

        if (hb.clickStarted) {
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.xScale * 0.98f, Settings.scale * 0.98f, 0.0f, 0, 0, 464, 98, false, false);
        } else {
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.xScale, Settings.scale, 0.0f, 0, 0, 464, 98, false, false);
        }

        if (this.flashTimer != 0.0f) {
            sb.setColor(0.6f, 1.0f, 1.0f, this.flashTimer * 1.5f);
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0f - 232.0f, this.y - 49.0f, 232.0f, 49.0f, 464.0f, 98.0f, Settings.xScale * 1.03f, Settings.scale * 1.15f, 0.0f, 0, 0, 464, 98, false, false);
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }

        float scale = renderCard.drawScale;

        renderCard.drawScale = 0.175f;
        renderCard.current_x = card.target_x = hb.x + ((AbstractCard.RAW_W * renderCard.drawScale) * Settings.scale) / 2f + XOFFSET;
        renderCard.current_y = card.target_y = hb.cY;
        renderCard.render(sb);

        renderCard.drawScale = scale;

        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, text, Settings.WIDTH * 0.434F, y + 5.0f * Settings.scale, 1000.0f * Settings.scale, 0.0f, col);
        //FontHelper.renderSmartText(sb, FontCreationPatches.tipFont, uiStrings.TEXT[1], REWARD_X_POS, this.y - FontHelper.getHeight(FontHelper.cardDescFont_N, text, Settings.scale) - 6f * Settings.scale, 1000.0f * Settings.scale, 0.0f, TIP_COL);

        if (hb.hovered || hb.justHovered) {
            CollectorMod.hoverRewardWorkaround = this;
        }

        hb.render(sb);

        sb.draw(TopPanelEssence.ICON, GOLD_IMG_X + 5.0F * Settings.scale, this.y - 30.0F * Settings.scale, GOLD_IMG_SIZE, GOLD_IMG_SIZE);
        Color c = Color.WHITE.cpy();
        if (EssenceSystem.essenceCount() < 3) {
            c = Color.SALMON.cpy();
        }
        FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, Integer.toString(3), GOLD_TEXT_X, this.y , 1000.0F * Settings.scale, 0.0F, c);
    }

    //Due to reward scrolling's orthographic camera and render order of rewards, the card needs to be rendered outside of the render method
    public void renderCardOnHover(SpriteBatch sb) {
        renderCard.current_x = card.target_x = InputHelper.mX + (AbstractCard.RAW_W * renderCard.drawScale) * Settings.scale;
        renderCard.current_y = card.target_y = InputHelper.mY;
        renderCard.render(sb);
    }
}
