package collector.cards;

import collector.powers.SufferingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Suffering extends AbstractCollectorCard {
    public final static String ID = makeID(Suffering.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public Suffering() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SufferingPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}