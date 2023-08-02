
package collector.cards;

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
        super(ID, 3, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 10;
    }

    public boolean returnToYou = false;

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        applyToEnemy(m, new DoomPower(m, magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(3);
    }
}

