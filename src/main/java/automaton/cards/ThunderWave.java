package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.Iterator;

public class ThunderWave extends AbstractBronzeCard {

    public final static String ID = makeID("ThunderWave");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public ThunderWave() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = FunctionHelper.functionsCompiledThisCombat;
        for (int i = 0; i < x; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int x = FunctionHelper.functionsCompiledThisCombat;

        if (x > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + x +  cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}