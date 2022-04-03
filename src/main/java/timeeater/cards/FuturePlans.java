package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.FuturePlansPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class FuturePlans extends AbstractTimeEaterCard {
    public final static String ID = makeID("FuturePlans");
    // intellij stuff power, self, uncommon, , , , , , 

    public FuturePlans() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FuturePlansPower(1));
    }

    public void upp() {
        isInnate = true;
        uDesc();
    }
}