package timeEater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeEater.powers.GoodEveningPower;

public class GoodEvening extends AbstractTimeCard {

    public final static String ID = makeID("GoodEvening");

    // intellij stuff power, self, rare

    public GoodEvening() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GoodEveningPower(2));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}