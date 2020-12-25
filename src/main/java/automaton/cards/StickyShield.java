package automaton.cards;

import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StickyShield extends AbstractBronzeCard {

    public final static String ID = makeID("StickyShield");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 11;
    private static final int UPG_BLOCK = 3;

    public StickyShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        cardsToPreview = new Slimed();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        shuffleIn(new Slimed());
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}