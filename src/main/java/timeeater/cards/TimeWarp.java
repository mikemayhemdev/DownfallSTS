package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.powers.TimeWarpPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.applyToSelf;

public class TimeWarp extends AbstractTimeEaterCard {
    public final static String ID = makeID("TimeWarp");
    // intellij stuff skill, self, rare, , , , , , 

    public TimeWarp() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new TimeWarpPower());
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}