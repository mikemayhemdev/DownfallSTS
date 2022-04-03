package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import hermit.cards.EternalForm;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class FinalMemory extends AbstractMemoryCard {
    public final static String ID = makeID("FinalMemory");
    // intellij stuff  rare,

    public FinalMemory() {
        super(ID, 1, CardRarity.RARE);
        exhaust = true;
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new DemonForm());
        retVal.add(new WraithForm());
        retVal.add(new EchoForm());
        retVal.add(new DevaForm());
        retVal.add(new EternalForm());
        return retVal;
    }
}