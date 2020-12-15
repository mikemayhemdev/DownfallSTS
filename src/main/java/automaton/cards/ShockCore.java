package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShockCore extends AbstractBronzeCard {

    public final static String ID = makeID("ShockCore");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 1;

    public ShockCore() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(AutomatonMod.CORE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.LIGHTNING);
        atb(new GainEnergyAction(1));
    }

    public void upp() {
        upgradeDamage(3);
    }
}