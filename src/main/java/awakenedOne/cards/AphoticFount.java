package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.powers.AphoticFountPower;
import awakenedOne.ui.OrbitingSpells;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class AphoticFount extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(AphoticFount.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public AphoticFount() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        AbstractCard c = new AphoticShield();
        c.upgrade();
        MultiCardPreview.add(this, new AphoticShield(), c);
        baseMagicNumber = magicNumber = 4;
        loadJokeCardImage(this, makeBetaCardPath(AphoticFount.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = new AphoticShield();
        spellCards.add(card);
        applyToSelf(new AphoticFountPower());
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}