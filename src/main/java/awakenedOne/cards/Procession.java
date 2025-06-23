package awakenedOne.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Procession extends AbstractAwakenedCard {
    public final static String ID = makeID(Procession.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Procession() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        cardsToPreview = new VoidCard();
        loadJokeCardImage(this, makeBetaCardPath(Procession.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
        atb(new MakeTempCardInDiscardAction(new VoidCard(), this.magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(-2);
    }
}