package automaton.cards;

import automaton.powers.FreeFirePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Subroutine extends AbstractBronzeCard {

    public final static String ID = makeID("Subroutine");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public Subroutine() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new FreeFirePower(1));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}