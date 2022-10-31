package collector.cards.Collectibles;

import collector.powers.MysticCharge;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
public class CrookedStaff extends AbstractCollectibleCard {
    public final static String ID = makeID("CrookedStaff");

    public CrookedStaff () {
        super(ID, 2, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MysticCharge(p,magicNumber,12));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
    }
}