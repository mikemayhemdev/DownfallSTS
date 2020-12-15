package automaton.cards;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForwardsDeclaration extends AbstractBronzeCard {

    public final static String ID = makeID("ForwardsDeclaration");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 1;

    public ForwardsDeclaration() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        shuffleBackIntoDrawPile = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ScryAction(1));
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}