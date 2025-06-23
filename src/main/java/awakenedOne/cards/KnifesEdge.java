package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class KnifesEdge extends AbstractAwakenedCard {
    public final static String ID = makeID(KnifesEdge.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public KnifesEdge() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
        cardsToPreview = new VoidCard();
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        addToBot(new MakeTempCardInDiscardAction(new VoidCard(), secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}