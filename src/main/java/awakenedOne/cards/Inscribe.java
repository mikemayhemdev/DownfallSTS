package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Inscribe extends AbstractAwakenedCard {
    public final static String ID = makeID(Inscribe.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public Inscribe() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Inscribe.class.getSimpleName() + ".png"));
        tags.add(DELVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) atb(new ConjureAction(false));
        atb(new ConjureAction(true, magicNumber, 0));
    }

    public void upp() {
    }
}