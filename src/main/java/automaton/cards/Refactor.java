package automaton.cards;

import automaton.actions.ScryBlockStatusAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Refactor extends AbstractBronzeCard {
    public final static String ID = makeID("Refactor");

    public Refactor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseBlock = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryBlockStatusAction(magicNumber, block));

    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
