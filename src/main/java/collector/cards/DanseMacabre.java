package collector.cards;

import collector.powers.DanseMacabrePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class DanseMacabre extends AbstractCollectorCard {
    public final static String ID = makeID(DanseMacabre.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 8, 2

    public DanseMacabre() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
        basemagic2 = magic2 = 16;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DanseMacabrePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeMagicNumber(4);
    }
}