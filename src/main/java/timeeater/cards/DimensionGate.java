package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import hermit.cards.Grudge;
import hermit.cards.LuckOfTheDraw;
import hermit.cards.Malice;
import timeeater.TimeEaterMod;
import timeeater.cards.alternateDimension.*;

import java.util.ArrayList;
import java.util.Collections;

import static timeeater.TimeEaterMod.makeID;

public class DimensionGate extends AbstractMemoryCard {
    public final static String ID = makeID("DimensionGate");
    // intellij stuff  uncommon,


    public DimensionGate() {
        super(ID, 1, CardRarity.RARE);
    }



    @Override
    protected ArrayList<AbstractCard> cards() {
        if (TimeEaterMod.dimensionCards.size() == 0)
            TimeEaterMod.initializeDimensionCardStrings();
        Collections.shuffle(TimeEaterMod.dimensionCards);
        ArrayList<AbstractCard> retVal = new ArrayList<>();

        //TODO this crashes!  Maybe a race condition? card library isn't fully initialized yet?
        //Ideally there is one stored place for the dimension card array that isn't a card
        //for performance reasons, no need to be duping the list of dimension cards all over.

      //  retVal.add(CardLibrary.getCard(TimeEaterMod.dimensionCards.get(0)).makeCopy());
      //  retVal.add(CardLibrary.getCard(TimeEaterMod.dimensionCards.get(1)).makeCopy());
       // retVal.add(CardLibrary.getCard(TimeEaterMod.dimensionCards.get(2)).makeCopy());
        return retVal;
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}