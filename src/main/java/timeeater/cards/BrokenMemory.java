package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Melter;
import com.megacrit.cardcrawl.cards.blue.ReinforcedBody;
import com.megacrit.cardcrawl.cards.blue.Scrape;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class BrokenMemory extends AbstractMemoryCard {
    public final static String ID = makeID("BrokenMemory");
    // intellij stuff  uncommon,

    public BrokenMemory() {
        super(ID, 0, CardRarity.UNCOMMON);
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new Melter());
        retVal.add(new ReinforcedBody());
        retVal.add(new Scrape());
        return retVal;
    }
}