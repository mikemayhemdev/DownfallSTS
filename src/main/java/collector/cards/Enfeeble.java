package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class Enfeeble extends AbstractCollectorCard {
    public final static String ID = makeID("Enfeeble");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Enfeeble() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, 1, false));
        applyToEnemy(m, new VulnerablePower(m, 1, false));
        atb(new DrawCardAction(magicNumber));

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}