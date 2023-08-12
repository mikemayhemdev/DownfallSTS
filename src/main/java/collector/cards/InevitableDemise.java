
package collector.cards;

import collector.powers.DemisePower;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class InevitableDemise extends AbstractCollectorCard {
    public final static String ID = makeID(InevitableDemise.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 4, , , ,

    public InevitableDemise() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
       // baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        applyToEnemy(m, new DemisePower(m, 1));
    }

    public void upp() {
        upgradeDamage(3);
       // upgradeMagicNumber(3);
    }
}

