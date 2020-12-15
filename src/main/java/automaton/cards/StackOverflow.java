package automaton.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StackOverflow extends AbstractBronzeCard {

    public final static String ID = makeID("StackOverflow");

    //stupid intellij stuff skill, self, rare

    public StackOverflow() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            atb(new GainEnergyAction(1));
        }
        fireBlaster(1);
        fireShields(1);
        fireCores(1);
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}