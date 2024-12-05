package collector.cards;

import com.megacrit.cardcrawl.actions.unique.FiendFireAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class FiendFire extends AbstractCollectorCard {
    public final static String ID = makeID(FiendFire.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 7, 3, , , , 

    public FiendFire() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new FiendFireAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
    }

    public void upp() {
        upgradeDamage(3);
    }
}