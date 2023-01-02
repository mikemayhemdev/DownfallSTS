package awakenedOne.cards;

import awakenedOne.powers.ZeroToHeroPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class ZeroToHero extends AbstractAwakenedCard {
    public final static String ID = makeID(ZeroToHero.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public ZeroToHero() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ZeroToHeroPower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}