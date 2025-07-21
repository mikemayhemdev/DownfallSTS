package awakenedOne.cards;

import awakenedOne.powers.InResponsePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Spellshield extends AbstractAwakenedCard {
    public final static String ID = makeID(Spellshield.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public Spellshield() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        //baseSecondMagic = secondMagic = 2;
        this.isEthereal = true;
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Spellshield.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new InResponsePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}