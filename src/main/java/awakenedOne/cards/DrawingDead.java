package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class DrawingDead extends AbstractAwakenedCard {
    public final static String ID = makeID(DrawingDead.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public DrawingDead() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(p, this.magicNumber));
        applyToSelf(new StrengthPower(p, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}