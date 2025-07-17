package awakenedOne.cards;

import awakenedOne.powers.ReverseRitualPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CuriosityPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class FleetingCuriosity extends AbstractAwakenedCard {
    public final static String ID = makeID(FleetingCuriosity.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 2, 1

    public FleetingCuriosity() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
        loadJokeCardImage(this, makeBetaCardPath(FleetingCuriosity.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelfTop(new CuriosityPower(p, secondMagic));
        applyToSelf(new ReverseRitualPower(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-1);
    }
}