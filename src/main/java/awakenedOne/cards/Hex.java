package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;

public class Hex extends AbstractAwakenedCard {
    public final static String ID = makeID(Hex.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Hex() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}