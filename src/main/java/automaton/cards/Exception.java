package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Exception extends AbstractBronzeCard {

    public final static String ID = makeID("Exception");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 5;

    private static final int BLOCK = 20;
    private static final int UPG_BLOCK = 5;

    public Exception() {
        super(ID, 100, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}