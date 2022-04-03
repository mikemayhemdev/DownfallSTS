package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import hermit.cards.Grudge;
import hermit.cards.LuckOfTheDraw;
import hermit.cards.Malice;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class DistantMemory extends AbstractMemoryCard {
    public final static String ID = makeID("DistantMemory");
    // intellij stuff  uncommon,

    public DistantMemory() {
        super(ID, 0, CardRarity.UNCOMMON);
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new Malice());
        retVal.add(new LuckOfTheDraw());
        retVal.add(new Grudge());
        return retVal;
    }
}