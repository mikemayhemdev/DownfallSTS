package collector.cards;

import collector.powers.FeelMyPainPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class FeelMyPain extends AbstractCollectorCard {
    public final static String ID = makeID(FeelMyPain.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public FeelMyPain() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FeelMyPainPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}