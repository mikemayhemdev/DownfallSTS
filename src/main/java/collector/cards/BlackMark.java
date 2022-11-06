package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.order;

public class BlackMark extends AbstractCollectorCard {
    public final static String ID = makeID(BlackMark.class.getSimpleName());
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