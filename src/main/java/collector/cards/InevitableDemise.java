package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static collector.CollectorMod.makeID;

public class InevitableDemise extends AbstractCollectorCard {
    public final static String ID = makeID(InevitableDemise.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 4, , , ,

    public InevitableDemise() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 2;
    }

    boolean returnToHand = false;

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        returnToHand = Wiz.pwrAmt(m, DoomPower.POWER_ID) >= 20;
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}