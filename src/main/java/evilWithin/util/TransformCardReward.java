package evilWithin.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import evilWithin.EvilWithinMod;

public class TransformCardReward extends RewardItem {
    public TransformCardReward() {
        this.hb = new Hitbox(460.0F * Settings.scale, 90.0F * Settings.scale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;

        this.text = "Transform a card in your deck.";
    }

    @Override
    public boolean claimReward() {
        EvilWithinMod.choosingTransformCard = true;
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
            EvilWithinMod.choosingTransformCard = false;
        } else {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, "Choose a Card to Remove", false, true, false, false);// 47 48
        }
        return true;
    }
}