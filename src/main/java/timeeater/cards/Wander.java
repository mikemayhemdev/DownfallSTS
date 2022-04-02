package timeeater.cards;

import champ.powers.DrawLessNextTurnPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Wander extends AbstractTimeEaterCard {
    public final static String ID = makeID("Wander");
    // intellij stuff skill, self, common, , , , , 3, 2

    public Wander() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryAction(magicNumber));
        atb(new DrawCardAction(1));
        applyToSelf(new DrawLessNextTurnPower(1));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}