package charbosses.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import downfall.downfallMod;

public class CBR_ToxicEgg extends AbstractCharbossRelic {
    public static final String ID = "ToxicEgg";
    int numCards = 0;


    public CBR_ToxicEgg() {
        super(new ToxicEgg2());
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + this.numCards + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void onTrigger() {
        numCards++;
        this.description = this.getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ToxicEgg();
    }
}
