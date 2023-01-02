package awakenedOne.cards;

import awakenedOne.powers.TowerOfEndingsPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class TowerOfEndings extends AbstractAwakenedCard {
    public final static String ID = makeID(TowerOfEndings.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public TowerOfEndings() {
        super(ID, 5, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new TowerOfEndingsPower());
    }

    public void upp() {
        upgradeBaseCost(4);
    }
}