package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForceShield extends AbstractBronzeCard {

    public final static String ID = makeID("ForceShield");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    public ForceShield() {
        super(ID, 4, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}