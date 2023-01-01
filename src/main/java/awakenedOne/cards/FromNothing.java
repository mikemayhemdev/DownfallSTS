package awakenedOne.cards;

import awakenedOne.powers.FromNothingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class FromNothing extends AbstractAwakenedCard {
    public final static String ID = makeID(FromNothing.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public FromNothing() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FromNothingPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}