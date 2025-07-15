package awakenedOne.cards;

import awakenedOne.powers.ThaumaturgyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class Thaumaturgy extends AbstractAwakenedCard {
    public final static String ID = makeID(Thaumaturgy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Thaumaturgy() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(Thaumaturgy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelfTop(new ThaumaturgyPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}