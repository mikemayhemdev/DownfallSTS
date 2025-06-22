package awakenedOne.cards;

import awakenedOne.powers.FeathersinksPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class ShroudOfMiasma extends AbstractAwakenedCard {
    public final static String ID = makeID(ShroudOfMiasma.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    //Shroud of Miasma

    public ShroudOfMiasma() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FeathersinksPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}