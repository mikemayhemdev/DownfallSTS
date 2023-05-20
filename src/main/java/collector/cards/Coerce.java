package collector.cards;

import collector.powers.SoulbindPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class Coerce extends AbstractCollectorCard {
    public final static String ID = makeID(Coerce.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public Coerce() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new SoulbindPower(m, 5));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}