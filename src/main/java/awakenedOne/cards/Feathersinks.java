package awakenedOne.cards;

import awakenedOne.powers.FeathersinksPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class Feathersinks extends AbstractAwakenedCard {
    public final static String ID = makeID(Feathersinks.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Feathersinks() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FeathersinksPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}