package downfall.util;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;
import downfall.patches.RewardItemTypeEnumPatch;

public class TransformCardReward extends CustomReward {
    public static final String ID = downfallMod.makeID("TransformCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public TransformCardReward() {
        super(TextureLoader.getTexture("downfallResources/images/rewards/placeholder.png"), TEXT[0], RewardItemTypeEnumPatch.TRANSFORMCARD);
    }

    @Override
    public boolean claimReward() {
        downfallMod.choosingTransformCard = true;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            downfallMod.choosingTransformCard = false;
        } else {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[1], false, true, false, false);
        }
        return true;
    }
}