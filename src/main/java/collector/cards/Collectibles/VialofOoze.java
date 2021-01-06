package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.powers.SlimedPower;

public class VialofOoze extends AbstractCollectibleCard {
    public final static String ID = makeID("VialofOoze");

    public VialofOoze() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        block = baseBlock = 2;
        magicNumber = baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new SlimedPower(m,p,magicNumber));
        blck();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeBlock(1);
    }
}
