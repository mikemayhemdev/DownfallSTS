package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.EmpressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class MoonlitVision extends AbstractAwakenedCard {
    public final static String ID = makeID(MoonlitVision.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public MoonlitVision() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(MoonlitVision.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            applyToSelfTop(new EmpressPower(p, 1));
    }

    @Override
    public void upp() {
        //this.isEthereal = false;
        upgradeBaseCost(1);
    }
}