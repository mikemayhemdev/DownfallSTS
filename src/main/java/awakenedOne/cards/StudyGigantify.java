package awakenedOne.cards;

import awakenedOne.actions.AddSpellCardAction;
import awakenedOne.cards.tokens.spells.GreaterGigantify;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class StudyGigantify extends AbstractAwakenedCard {
    public final static String ID = makeID(StudyGigantify.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 1, 1

    public StudyGigantify() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new GreaterGigantify();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AddSpellCardAction(new GreaterGigantify()));
    }

    public void upp() {
        isInnate = true;
    }
}