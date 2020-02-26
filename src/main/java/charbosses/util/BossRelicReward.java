package charbosses.util;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import evilWithin.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;

public class BossRelicReward extends RewardItem {
    public boolean cardsSelected = true;

    public BossRelicReward() {
        this.hb = new Hitbox(460.0F * Settings.scale, 90.0F * Settings.scale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;

        this.text = "Choose 1 of 3 Boss Relics to obtain.";
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardsSelected) {// 61 62
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, RelicLibrary.getRelic(AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID));
        }
    }

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

        for (String q : AbstractDungeon.bossRelicPool) {
            tmp.addToTop(new OctoChoiceCard(q, q.replaceAll("(.)([A-Z])", "$1 $2"), expansionContentMod.makeCardPath("QuickGuardian.png"), "Obtain a " + q.replaceAll("(.)([A-Z])", "$1 $2") + "."));
        }

        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, "Choose a Relic.. sorta", true, false, false, false);// 47 48
        return true;
    }
}