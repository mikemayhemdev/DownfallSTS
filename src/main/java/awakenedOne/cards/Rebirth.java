package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class Rebirth extends AbstractAwakenedCard {
    public final static String ID = makeID(Rebirth.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Rebirth() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 1;
        exhaust = true;
        cardsToPreview = new Miracle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new HealAction(p, p, magicNumber));
        AbstractCard q = new Miracle();
        if (upgraded) q.upgrade();
        makeInHand(q);
        applyToSelf(new StrengthPower(p, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(2);
        AbstractCard q2 = new Miracle();
        q2.upgrade();
        cardsToPreview = q2;
    }
}