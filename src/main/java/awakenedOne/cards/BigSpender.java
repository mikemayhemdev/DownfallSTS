package awakenedOne.cards;

import awakenedOne.powers.BigSpenderPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class BigSpender extends AbstractAwakenedCard {
    public final static String ID = makeID(BigSpender.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public BigSpender() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BigSpenderPower(1));
    }

    public void upp() {
        isInnate = true;
    }
}