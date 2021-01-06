package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHexaghost.powers.BurnPower;

public class Fever extends AbstractCollectorCard {
    public final static String ID = makeID("Fever");

    public Fever() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new BurnPower(m, magicNumber));
        applyToEnemy(m, new WeakPower(m, 1, false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }
}