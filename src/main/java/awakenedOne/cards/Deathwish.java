package awakenedOne.cards;

import awakenedOne.actions.AddSpellCardAction;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.DeathCoil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Deathwish extends AbstractAwakenedCard {
    public final static String ID = makeID(Deathwish.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 7, 3, , , ,

    public Deathwish() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new DeathCoil();
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new AddSpellCardAction(new DeathCoil()));
        atb(new ConjureAction(false));
    }

    public void upp() {
       isInnate = true;
    }
}