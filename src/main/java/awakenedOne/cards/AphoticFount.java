package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.atb;

public class AphoticFount extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(ArcaneNesting.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public AphoticFount() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        cardsToPreview = new AphoticShield();
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(ArcaneNesting.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = new AphoticShield();
        spellCards.add(new OrbitingSpells.CardRenderInfo(card));
        updateTimeOffsets();

        atb(new ConjureAction(false, false, true, new AphoticShield()));
    }

    @Override
    public void upp() {
        this.isInnate = true;
    }
}