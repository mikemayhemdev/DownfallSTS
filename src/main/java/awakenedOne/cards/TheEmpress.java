package awakenedOne.cards;

import awakenedOne.powers.EmpressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class TheEmpress extends AbstractAwakenedCard {
    public final static String ID = makeID(TheEmpress.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public TheEmpress() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            applyToSelfTop(new EmpressPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}