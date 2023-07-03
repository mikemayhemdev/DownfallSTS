package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class Kill extends AbstractCollectorCard {
    public final static String ID = makeID(Kill.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 50, , , , , 

    public Kill() {
        super(ID, 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 60;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new DoomPower(m, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(3);
    }
}