package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChildClass extends AbstractBronzeCard {

    public final static String ID = makeID("ChildClass");

    //stupid intellij stuff skill, self, uncommon

    public ChildClass() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean onOutput() {
        makeInHand(FunctionHelper.makeFunction(true));
        return true;
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}