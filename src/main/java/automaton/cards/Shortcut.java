package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Shortcut extends AbstractBronzeCard {

    public final static String ID = makeID("Shortcut");

    //stupid intellij stuff attack, enemy, basic

    private static final int BLOCK = 4;
    private static final int UPG_DAMAGE = 1;

    public Shortcut() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Replicate.png"));
        cardsToPreview = new Defend();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeBlock(2);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();

    }
}