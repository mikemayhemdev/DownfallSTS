package awakenedOne.cards;

import awakenedOne.powers.MoonlitVisionPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class MoonlitVision extends AbstractAwakenedCard {
    public final static String ID = makeID(MoonlitVision.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public MoonlitVision() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(MoonlitVision.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MoonlitVisionPower());
    }


    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}