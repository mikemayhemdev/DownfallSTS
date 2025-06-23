package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.DeathCoil;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.atb;

public class Deathwish extends AbstractAwakenedCard {
    public final static String ID = makeID(Deathwish.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 7, 3, , , ,

    public Deathwish() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new DeathCoil();
        this.exhaust = true;
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(Deathwish.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = new DeathCoil();
        spellCards.add(new OrbitingSpells.CardRenderInfo(card));
        updateTimeOffsets();
        atb(new ConjureAction(false, false, true, new DeathCoil()));
    }

    public void upp() {
       isInnate = true;
    }
}