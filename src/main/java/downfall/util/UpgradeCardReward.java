package downfall.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;
import downfall.patches.RewardItemTypeEnumPatch;

public class UpgradeCardReward extends CustomReward {
    public static final String ID = downfallMod.makeID("UpgradeCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public UpgradeCardReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0], RewardItemTypeEnumPatch.UPGRADECARD);
    }

    @Override
    public boolean claimReward() {
        downfallMod.choosingUpgradeCard = true;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard card : AbstractDungeon.player.masterDeck.getUpgradableCards().group) {
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            downfallMod.choosingUpgradeCard = false;
        } else {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, TEXT[1], true, false, false, false);
        }
        return true;
    }
}