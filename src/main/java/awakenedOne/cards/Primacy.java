package awakenedOne.cards;

import awakenedOne.powers.InResponsePower;
import awakenedOne.powers.PrimacyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Primacy extends AbstractAwakenedCard {
    public final static String ID = makeID(Primacy.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public Primacy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Primacy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PrimacyPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}