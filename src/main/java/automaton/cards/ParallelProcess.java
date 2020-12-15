package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ParallelProcess extends AbstractBronzeCard {

    public final static String ID = makeID("ParallelProcess");

    //stupid intellij stuff skill, self, uncommon

    public ParallelProcess() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        fireBlaster(1);
        fireShields(1);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}