package downfall.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;

public class RemoveCardReward extends RewardItem {
    public static final String ID = downfallMod.makeID("RemoveCardReward");
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;


    public RemoveCardReward() {
        this.hb = new Hitbox(460.0F * Settings.scale, 90.0F * Settings.scale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;

        this.text = TEXT[0];
    }

    @Override
    public boolean claimReward() {
        downfallMod.choosingRemoveCard = true;
        if (AbstractDungeon.isScreenUp) {// 29
            AbstractDungeon.dynamicBanner.hide();// 30
            AbstractDungeon.overlayMenu.cancelButton.hide();// 31
            AbstractDungeon.previousScreen = AbstractDungeon.screen;// 32
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;// 34
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);// 36

        for (AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group) {
            tmp.addToTop(card);// 38
        }

        if (tmp.group.isEmpty()) {// 41
            downfallMod.choosingRemoveCard = false;
        } else {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[1], false, false, false, true);// 47 48
        }
        return true;
    }
}