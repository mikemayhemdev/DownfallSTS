package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Shield extends AbstractBronzeCard {

    public final static String ID = makeID("Shield");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 11;
    private static final int UPG_BLOCK = 3;

    public Shield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        selfRetain = true;
        cardsToPreview = new Slimed();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Shield.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        shuffleIn(new Slimed());
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}