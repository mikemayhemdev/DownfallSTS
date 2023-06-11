package collector.cards;

import collector.powers.AlwaysMorePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class AlwaysMore extends AbstractCollectorCard {
    public final static String ID = makeID(AlwaysMore.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public AlwaysMore() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AlwaysMorePower());
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}