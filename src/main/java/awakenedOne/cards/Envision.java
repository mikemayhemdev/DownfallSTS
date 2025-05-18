package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Envision extends AbstractAwakenedCard {
    public final static String ID = makeID(Envision.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Envision() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (!upgraded) {
        atb(new ConjureAction(false));
        }

        if (upgraded) {
        atb(new ConjureAction(false));
        }
    }

    public void upp() {
        upgradeBlock(1);
    }
}