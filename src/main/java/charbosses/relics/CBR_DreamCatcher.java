package charbosses.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import downfall.downfallMod;

public class CBR_DreamCatcher extends AbstractCharbossRelic {
    public static final String ID = "DreamCatcher";
    private int numCards;

    public CBR_DreamCatcher() {
        super(new DreamCatcher());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DreamCatcher();
    }
}
