package charbosses.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import downfall.downfallMod;

public class CBR_PrayerWheel extends AbstractCharbossRelic {
    public static final String ID = "PrayerWheel";
    private int numCards;

    public CBR_PrayerWheel() {
        super(new PrayerWheel());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + this.numCards + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PrayerWheel();
    }
}
