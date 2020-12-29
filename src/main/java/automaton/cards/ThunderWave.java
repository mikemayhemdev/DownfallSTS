package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThunderWave extends AbstractBronzeCard {

    public final static String ID = makeID("ThunderWave");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public ThunderWave() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = FunctionHelper.functionsCompiledThisCombat;
        for (int i = 0; i < x; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}