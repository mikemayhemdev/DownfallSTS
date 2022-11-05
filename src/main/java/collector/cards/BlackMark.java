package collector.cards;

import collector.actions.OrderAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.CollectorMod.order;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class BlackMark extends AbstractCollectorCard {
    public final static String ID = makeID("BlackMark");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public BlackMark() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        order();
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}