package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Constructor extends AbstractBronzeCard {

    public final static String ID = makeID("Constructor");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    public Constructor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        thisEncodes();
    }

    @Override
    public void onInput() {
        if (firstCard()) {
            baseBlock *= 2;
            block *= 2;
            superFlash();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}