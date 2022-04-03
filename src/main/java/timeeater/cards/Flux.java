package timeeater.cards;

import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Flux extends AbstractTimeEaterCard {
    public final static String ID = makeID("Flux");
    // intellij stuff skill, self, uncommon, , , , , 2, 1

    public Flux() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardToPreview.add(new Shiv());
        cardToPreview.add(new Doubt());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, 4));
        applyToSelf(new LoseStrengthPower(p, 4));
        makeInHand(new Shiv(), magicNumber);
        atb(new SuspendAction(new Doubt()));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}