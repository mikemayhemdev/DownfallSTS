package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class Baptism extends AbstractAwakenedCard {
    public final static String ID = makeID(Baptism.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Baptism() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 7;
        exhaust = true;
        cardsToPreview = new VoidCard();
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new HealAction(p, p, magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}