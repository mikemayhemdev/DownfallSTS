package awakenedOne.cards;

import awakenedOne.powers.ThaumaturgyPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class Thaumaturgy extends AbstractAwakenedCard {
    public final static String ID = makeID(Thaumaturgy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Thaumaturgy() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagic = 2;
        this.secondMagic = this.baseSecondMagic;
        loadJokeCardImage(this, makeBetaCardPath(Thaumaturgy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelfTop(new ThaumaturgyPower(magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}