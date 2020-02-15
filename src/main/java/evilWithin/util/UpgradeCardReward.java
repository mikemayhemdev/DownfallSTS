package evilWithin.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class UpgradeCardReward extends RewardItem {
    public UpgradeCardReward() {
        this.hb = new Hitbox(460.0F * Settings.scale, 90.0F * Settings.scale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;

        this.text = "Remove a card in your deck.";
    }

    public boolean cardsSelected = true;

    @Override
    public void update() {
        super.update();
        if (!this.cardsSelected) {// 61 62
            this.removeCards(AbstractDungeon.gridSelectScreen.selectedCards.get(0));// 63
        }
    }

    public void removeCards(AbstractCard card) {
        this.cardsSelected = true;// 69
        card.untip();// 73
        card.unhover();// 74
        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float) Settings.WIDTH / 2, (float) Settings.HEIGHT / 2.0F));// 75
        AbstractDungeon.player.masterDeck.removeCard(card);// 78
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;// 81
        AbstractDungeon.gridSelectScreen.selectedCards.clear();// 82
    }// 83

    @Override
    public boolean claimReward() {
        this.cardsSelected = false;// 28
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
            this.cardsSelected = true;// 42
        } else {
            if (tmp.group.size() == 1) {// 44
                this.removeCards(tmp.getRandomCard(true));// 45
            } else {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, "Choose a Card to Remove", false, false, false, true);// 47 48
            }
        }
        return true;
    }
}