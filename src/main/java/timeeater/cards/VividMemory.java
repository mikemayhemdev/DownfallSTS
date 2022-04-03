package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Footwork;
import com.megacrit.cardcrawl.cards.purple.Fasting;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractMemoryCard;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class VividMemory extends AbstractMemoryCard {
    public final static String ID = makeID("VividMemory");
    // intellij stuff  uncommon,

    public VividMemory() {
        super(ID, 1, CardRarity.UNCOMMON);
    }

    @Override
    protected ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        retVal.add(new Inflame());
        retVal.add(new Footwork());
        retVal.add(new Fasting());
        return retVal;
    }
}