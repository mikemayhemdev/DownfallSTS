package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;

public class ActOfAggression extends AbstractCollectorCard {
    public final static String ID = makeID(ActOfAggression.class.getSimpleName());
    // intellij stuff , , , , , 6, , 1, 1

    public ActOfAggression() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL);
        baseBlock = 6;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(q -> applyToEnemy(q, new VulnerablePower(q, magicNumber, false)));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}