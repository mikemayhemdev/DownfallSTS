package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.awakenedAmt;

public class Flourish extends AbstractAwakenedCard {
    public final static String ID = makeID(Flourish.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 3, 1

    public Flourish() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmt();
        if (amt >= 2) {
            atb(new DrawCardAction(magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}