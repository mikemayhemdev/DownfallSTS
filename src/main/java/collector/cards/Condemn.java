package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class Condemn extends AbstractCollectorCard {
    public final static String ID = makeID(Condemn.class.getSimpleName());
    // intellij stuff attack, enemy, common, 3, 1, , , 2, 

    public Condemn() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new DoomPower(m, magicNumber));
        applyToEnemy(m, new VulnerablePower(m, 1, false));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }
}