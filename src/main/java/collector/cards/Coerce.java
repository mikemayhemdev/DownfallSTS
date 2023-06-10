package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;

public class Coerce extends AbstractCollectorCard {
    public final static String ID = makeID(Coerce.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public Coerce() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        isPyre();
        baseMagicNumber = magicNumber = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(q -> applyToEnemy(q, new DoomPower(q, magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}