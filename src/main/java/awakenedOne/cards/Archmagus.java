package awakenedOne.cards;

import awakenedOne.powers.ArchmagusPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Archmagus extends AbstractAwakenedCard {
    public final static String ID = makeID(Archmagus.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Archmagus() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(Archmagus.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ArchmagusPower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }

}