package evilWithin.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import evilWithin.EvilWithinMod;
import evilWithin.cards.OctoChoiceCard;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.CenterGridCardSelectScreen;

import java.util.ArrayList;

public class BossRelicReward extends RewardItem {
    public BossRelicReward() {
        this.hb = new Hitbox(460.0F * Settings.scale, 90.0F * Settings.scale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;

        this.text = "Choose 1 of 3 Boss Relics to obtain.";
    }

    @Override
    public boolean claimReward() {
        EvilWithinMod.choosingBossRelic = true;
        if (AbstractDungeon.isScreenUp) {// 29
            AbstractDungeon.dynamicBanner.hide();// 30
            AbstractDungeon.overlayMenu.cancelButton.hide();// 31
            AbstractDungeon.previousScreen = AbstractDungeon.screen;// 32
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;// 34
        ArrayList<AbstractCard> tmp = new ArrayList<>();

        for (String q : AbstractDungeon.bossRelicPool) {
            String d = q;
            if (q.contains(":")) {
                d = q.replaceAll("(.)([A-Z])", "$1 $2").substring(q.indexOf(":") + 1);
                d = d.trim();
            }
            tmp.add(new OctoChoiceCard(q, d, expansionContentMod.makeCardPath("QuickGuardian.png"), "Obtain a " + d + "."));
        }

        CardGroup qqq = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (int i = 0; i < 3; i++) {
            qqq.addToTop(tmp.remove(AbstractDungeon.cardRandomRng.random(tmp.size() - 1)));
        }

        CenterGridCardSelectScreen.centerGridSelect = true;
        AbstractDungeon.gridSelectScreen.open(qqq, 1, "Choose a Relic.. sorta", false, false, false, false);// 47 48
        return true;
    }
}