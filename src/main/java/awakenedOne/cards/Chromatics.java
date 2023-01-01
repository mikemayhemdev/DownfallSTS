package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Chromatics extends AbstractAwakenedCard {
    public final static String ID = makeID(Chromatics.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 6, 2, 2, 1

    public Chromatics() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 6;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
        atb(new ConjureAction(magicNumber));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}