package awakenedOne.cards;

import awakenedOne.powers.DogmaPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
@Deprecated
@CardIgnore
public class Dogma extends AbstractAwakenedCard {
    public final static String ID = makeID(Dogma.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Dogma() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DogmaPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}