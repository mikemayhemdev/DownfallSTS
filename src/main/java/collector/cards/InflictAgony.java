package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.isAfflicted;

public class InflictAgony extends AbstractCollectorCard {
    public final static String ID = makeID(InflictAgony.class.getSimpleName());
    // intellij stuff attack, enemy, common, 13, 5, , , , 

    public InflictAgony() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (!isAfflicted(m)) {
            applyToEnemy(m, new WeakPower(m, 2, false));
            applyToEnemy(m, new VulnerablePower(m, 2, false));
        }
    }

    public void upp() {
        upgradeDamage(6);
    }
}