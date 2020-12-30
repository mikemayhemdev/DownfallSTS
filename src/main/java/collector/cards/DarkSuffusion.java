package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class DarkSuffusion extends AbstractCollectorCard {
    public final static String ID = makeID("DarkSuffusion");

    public DarkSuffusion() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 5;
        baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new PoisonPower(m,p,magicNumber));
        applyToEnemy(m, new VulnerablePower(m,1,false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}