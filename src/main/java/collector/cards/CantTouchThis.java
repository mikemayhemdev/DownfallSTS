package collector.cards;

import collector.powers.CantTouchThisPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class CantTouchThis extends AbstractCollectorCard {
    public final static String ID = makeID(CantTouchThis.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 1, 1

    public CantTouchThis() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, magicNumber));
        applyToSelf(new CantTouchThisPower(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}