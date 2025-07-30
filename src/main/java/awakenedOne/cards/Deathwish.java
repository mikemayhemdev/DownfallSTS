package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.util.Wiz.atb;
@Deprecated
@CardIgnore
public class Deathwish extends AbstractAwakenedCard {
    public final static String ID = makeID(Deathwish.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 7, 3, , , ,

    public Deathwish() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        AbstractCard c = new DeathCoil();
        c.upgrade();
        MultiCardPreview.add(this, new DeathCoil(), c);
        this.exhaust = true;
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(Deathwish.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = new DeathCoil();
        spellCards.add(card);
        atb(new ConjureAction(false, false, true, new DeathCoil()));
    }

    public void upp() {
        isInnate = true;
    }
}