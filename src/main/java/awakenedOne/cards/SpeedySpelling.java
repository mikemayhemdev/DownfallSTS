package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class SpeedySpelling extends AbstractAwakenedCard {
    public final static String ID = makeID(SpeedySpelling.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public SpeedySpelling() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ConjureAction(1));
    }

    public void upp() {
        selfRetain = true;
    }
}