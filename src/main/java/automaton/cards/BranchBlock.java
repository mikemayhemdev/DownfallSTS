package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

@CardIgnore
public class BranchBlock extends AbstractBronzeCard {

    public final static String ID = makeID("BranchBlock");

    //stupid intellij stuff attack, self_and_enemy, common

    private static final int BLOCK = 9;

    public BranchBlock() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(2);
    }
}