package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.UnknownClass;
import sneckomod.util.ColorfulCardReward;
import sneckomod.util.ColorfulRareReward;
import sneckomod.util.ColorfulUncommonUpgradedReward;

public class SneckoBoss extends CustomRelic implements CustomSavable<AbstractCard.CardColor> {

    public static final String ID = SneckoMod.makeID("SneckoBoss");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("LuckyHorseshoe.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("LuckyHorseshoe.png"));

    public static AbstractCard.CardColor myColor = null;
    private boolean chosenInGeneral = false;

    public SneckoBoss() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        myColor = null;
        chosenInGeneral = false;
        openColorSelection();
    }

    private void openColorSelection() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

        CardGroup selectionGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card instanceof UnknownClass && (SneckoMod.validColors.contains(((UnknownClass) card).myColor) || SneckoMod.isPureSneckoModeEnabled() || AbstractDungeon.player.hasRelic(PrismaticShard.ID))) {
                selectionGroup.addToTop(card.makeCopy());
            }
        }
        AbstractDungeon.gridSelectScreen.open(selectionGroup, 1, false, "");
    }


    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !chosenInGeneral) {
            chosenInGeneral = true;
            AbstractCard selectedCardsnek = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if (selectedCardsnek instanceof UnknownClass) {
                myColor = ((UnknownClass) selectedCardsnek).myColor;
                createColorSpecificRewards();
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                updateDescriptionAndTips();
            }
        }
    }

    private void createColorSpecificRewards() {
        // Add 3 upgraded uncommon rewards
        for (int i = 0; i < 3; i++) {
            AbstractDungeon.getCurrRoom().rewards.add(new ColorfulUncommonUpgradedReward(myColor));
        }

        // Add 2 rare rewards
        for (int i = 0; i < 2; i++) {
            AbstractDungeon.getCurrRoom().rewards.add(new ColorfulRareReward(myColor));
        }

        AbstractDungeon.combatRewardScreen.open();
        AbstractDungeon.combatRewardScreen.rewards.remove(AbstractDungeon.combatRewardScreen.rewards.size()-1);
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.05F;
    }

    private void updateDescriptionAndTips() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription() {
        if (myColor != null) {
            return DESCRIPTIONS[1] + SneckoMod.getClassFromColor(myColor) + DESCRIPTIONS[2] + SneckoMod.getClassFromColor(myColor) + DESCRIPTIONS[3];
        }
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractCard.CardColor onSave() {
        return myColor;
    }

    @Override
    public void onLoad(AbstractCard.CardColor color) {
        myColor = color;
        updateDescriptionAndTips();
    }

    @Override
    public void onVictory() {
        AbstractDungeon.getCurrRoom().rewards.add(new ColorfulCardReward(myColor));
    }
}
