package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.LibrarianPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class GrandMagus extends AbstractAwakenedCard {
    public final static String ID = makeID(GrandMagus.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public GrandMagus() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(GrandMagus.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LibrarianPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}