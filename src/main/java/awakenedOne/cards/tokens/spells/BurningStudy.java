package awakenedOne.cards.tokens.spells;

import awakenedOne.cards.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class BurningStudy extends AbstractSpellCard {
    public final static String ID = makeID(BurningStudy.class.getSimpleName());
    // intellij stuff skill, self, , , , , 2, 1

    public BurningStudy() {
        super(ID, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ExhaustAction(1, false));
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}