package awakenedOne.cards;

import awakenedOne.powers.RisingChantPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class RisingChant extends AbstractAwakenedCard {
    public final static String ID = makeID(RisingChant.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RisingChant() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RisingChantPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}