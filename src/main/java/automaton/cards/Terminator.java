package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Terminator extends AbstractBronzeCard {

    public final static String ID = makeID("Terminator");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public Terminator() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = baseDamage;
        if (inFunc) {
            if (lastCard()) {
                baseDamage *= 2;
            }
        }
        super.applyPowers();
        baseDamage = realBaseDamage;
        this.isDamageModified = damage != baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = baseDamage;
        if (inFunc) {
            if (lastCard()) {
                baseDamage *= 2;
            }
        }
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;
        this.isDamageModified = damage != baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}