package awakenedOne.cards;

import awakenedOne.powers.PrimacyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class Primacy extends AbstractAwakenedCard {
    public final static String ID = makeID(Primacy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Primacy() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        //this.isEthereal = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PrimacyPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}