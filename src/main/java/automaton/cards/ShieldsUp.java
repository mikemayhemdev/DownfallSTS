package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldsUp extends AbstractBronzeCard {

    public final static String ID = makeID("ShieldsUp");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    public ShieldsUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        tags.add(AutomatonMod.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}