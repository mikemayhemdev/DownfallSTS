package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cards.encodedcards.EncodedLeap;
import automaton.cards.encodedcards.EncodedMiracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Constructor extends AbstractBronzeCard {

    public final static String ID = makeID("Constructor");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public Constructor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        cardsToPreview = new EncodedLeap();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (FunctionHelper.held.group.size() == 0){
            addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
        } else {
            addCardToFunction(new Defend(), upgraded);
        }
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}