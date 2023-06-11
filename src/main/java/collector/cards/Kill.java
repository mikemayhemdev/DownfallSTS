package collector.cards;

import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Kill extends AbstractCollectorCard {
    public final static String ID = makeID(Kill.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 50, , , , , 

    public Kill() {
        super(ID, 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 50;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeBaseCost(3);
    }
}