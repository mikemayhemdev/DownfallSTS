package awakenedOne.cards;

import awakenedOne.powers.HemorrhageDebuff;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;
@Deprecated
@CardIgnore
public class LoseFaith extends AbstractAwakenedCard {

    public final static String ID = makeID(LoseFaith.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public LoseFaith() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        this.baseSecondMagic = 4;
        this.secondMagic = this.baseSecondMagic;

        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    atb(new DrawCardAction(p, this.magicNumber));
    applyToSelf(new HemorrhageDebuff(this.secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

}
