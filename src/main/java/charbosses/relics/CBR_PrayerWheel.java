package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import com.megacrit.cardcrawl.relics.PrayerWheel;

import java.util.ArrayList;

public class CBR_PrayerWheel extends AbstractCharbossRelic {

    public CBR_PrayerWheel() {
        super(new PrayerWheel());
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        for (int i = AbstractDungeon.actNum; i < 3; i++) {
            AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Prayer Wheel");
            AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Prayer Wheel");
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PrayerWheel();
    }
}
