package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.cards.green.Choke;
import com.megacrit.cardcrawl.cards.green.Predator;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class QuietMemory extends AbstractMemoryCard {
    public final static String ID = makeID("QuietMemory");
    // intellij stuff  uncommon,

    public QuietMemory() {
        super(ID, 0, CardRarity.UNCOMMON);
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new Choke());
        retVal.add(new Blur());
        retVal.add(new Predator());
        return retVal;
    }
}