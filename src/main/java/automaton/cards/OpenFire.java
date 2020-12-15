package automaton.cards;

import automaton.MechaHelper;
import automaton.actions.FireFromPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OpenFire extends AbstractBronzeCard {

    public final static String ID = makeID("OpenFire");

    //stupid intellij stuff skill, self, basic

    public OpenFire() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        fireBlaster(1);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}