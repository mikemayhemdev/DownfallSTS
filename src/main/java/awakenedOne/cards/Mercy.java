package awakenedOne.cards;

import awakenedOne.powers.MercyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
@Deprecated
@CardIgnore
public class Mercy extends AbstractAwakenedCard {
    public final static String ID = makeID(Mercy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Mercy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MercyPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}