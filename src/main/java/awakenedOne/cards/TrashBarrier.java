package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class TrashBarrier extends AbstractAwakenedCard {
    public final static String ID = makeID(TrashBarrier.class.getSimpleName());
    // intellij stuff skill, self, common, , , 5, 3, , 

    public TrashBarrier() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ExhaustAction(1, false));
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}