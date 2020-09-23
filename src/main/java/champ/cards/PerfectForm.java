package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PerfectForm extends AbstractChampCard {

    public final static String ID = makeID("PerfectForm");

    //stupid intellij stuff power, self, rare

    public PerfectForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ultimateStance();
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}