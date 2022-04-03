package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.FlameBarrier;
import com.megacrit.cardcrawl.cards.red.Uppercut;
import com.megacrit.cardcrawl.cards.red.Whirlwind;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class EnragedMemory extends AbstractMemoryCard {
    public final static String ID = makeID("EnragedMemory");
    // intellij stuff  uncommon,

    public EnragedMemory() {
        super(ID, 0, CardRarity.UNCOMMON);
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new Whirlwind());
        retVal.add(new Uppercut());
        retVal.add(new FlameBarrier());
        return retVal;
    }
}