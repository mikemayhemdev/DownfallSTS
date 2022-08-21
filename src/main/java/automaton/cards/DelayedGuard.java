package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedCutThroughFate;
import automaton.cards.encodedcards.EncodedDodgeAndRoll;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class DelayedGuard extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedGuard");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public DelayedGuard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        cardsToPreview = new EncodedDodgeAndRoll();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());

    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}