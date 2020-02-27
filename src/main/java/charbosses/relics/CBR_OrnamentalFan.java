package charbosses.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import evilWithin.EvilWithinMod;

public class CBR_OrnamentalFan extends AbstractCharbossRelic {
    public static final String ID = "Omamori";


    public CBR_OrnamentalFan() {
        super(new Omamori());
    }

    public void use(String cardName) {
        this.flash();
        --this.counter;
        this.description = this.description + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0] + cardName + ".";

        if (this.counter == 0) {
            this.setCounter(0);
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_OrnamentalFan();
    }
}
