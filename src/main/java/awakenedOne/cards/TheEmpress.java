package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.EmpressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class TheEmpress extends AbstractAwakenedCard {
    public final static String ID = makeID(TheEmpress.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public TheEmpress() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            applyToSelfTop(new EmpressPower(p, 1));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}