package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Perseverance;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.cards.purple.Wallop;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;

public class DivineMemory extends AbstractMemoryCard {
    public final static String ID = makeID("DivineMemory");
    // intellij stuff  uncommon,

    public DivineMemory() {
        super(ID, 0, CardRarity.UNCOMMON);
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new Wallop());
        retVal.add(new Sanctity());
        retVal.add(new Perseverance());
        return retVal;
    }
}