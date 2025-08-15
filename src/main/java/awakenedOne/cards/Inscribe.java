package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.powers.PrimacyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class Inscribe extends AbstractAwakenedCard {
    public final static String ID = makeID(Inscribe.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public Inscribe() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Inscribe.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ConjureAction(true, 1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}