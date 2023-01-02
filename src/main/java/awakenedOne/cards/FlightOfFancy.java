package awakenedOne.cards;

import awakenedOne.cards.tokens.Feather;
import awakenedOne.powers.FlightOfFancyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class FlightOfFancy extends AbstractAwakenedCard {
    public final static String ID = makeID(FlightOfFancy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 2, 

    public FlightOfFancy() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Feather();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FlightOfFancyPower(magicNumber));
    }

    public void upp() {
        isInnate = true;
    }
}