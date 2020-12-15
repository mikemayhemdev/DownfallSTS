package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Infusion extends AbstractBronzeCard {

    public final static String ID = makeID("Infusion");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;

    public Infusion() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        fireCores(1);
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}