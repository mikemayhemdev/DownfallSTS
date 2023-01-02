package awakenedOne.cards;

import awakenedOne.actions.ModifyMagicAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class DrainToDust extends AbstractAwakenedCard {
    public final static String ID = makeID(DrainToDust.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , 6, 2

    public DrainToDust() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 6;
        baseSecondMagic = secondMagic = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LoseHPAction(m, p, magicNumber));
        atb(new ModifyMagicAction(this.uuid, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(2);
    }
}