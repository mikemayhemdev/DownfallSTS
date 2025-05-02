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
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FeathersinksPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}