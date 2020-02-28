package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class CBR_PrayerWheel extends AbstractCharbossRelic {
    public static final String ID = "PrayerWheel";
    private int numCards;

    public CBR_PrayerWheel() {
        super(new PrayerWheel());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0] + this.numCards + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        for (int i = actIndex; i < 3; i++) {
            AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Prayer Wheel");
            AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Prayer Wheel");
            numCards += 2;
        }

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PrayerWheel();
    }
}
