package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class SapStrength extends AbstractCollectorCard {
    public final static String ID = makeID(SapStrength.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 24, 8, , , , 

    public SapStrength() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 27;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        AbstractPower enemyStr = m.getPower(StrengthPower.POWER_ID);
        if (enemyStr != null) {
            if (enemyStr.amount > 0) {
                int toSteal = Math.min(2, enemyStr.amount);
                atb(new ReducePowerAction(m, p, StrengthPower.POWER_ID, toSteal));
                applyToSelf(new StrengthPower(p, toSteal));
            }
        }
    }

    public void upp() {
        upgradeDamage(8);
    }
}