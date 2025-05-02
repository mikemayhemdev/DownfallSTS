package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class CawCaw extends AbstractAwakenedCard {
    public final static String ID = makeID(CawCaw.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 2, 1

    public CawCaw() {
        super(ID, 4, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RitualPower(p, magicNumber, true));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}