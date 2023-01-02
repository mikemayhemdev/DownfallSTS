package awakenedOne.cards;

import awakenedOne.powers.BlotOutPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;

public class BlotOut extends AbstractAwakenedCard {
    public final static String ID = makeID(BlotOut.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , , 

    public BlotOut() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new BlotOutPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}