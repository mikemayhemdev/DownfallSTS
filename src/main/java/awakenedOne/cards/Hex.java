package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToEnemy;
@Deprecated
@CardIgnore
public class Hex extends AbstractAwakenedCard {
    public final static String ID = makeID(Hex.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Hex() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;

        this.baseSecondMagic = 3;
        this.secondMagic = this.baseSecondMagic;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
        //HexCurse(secondMagic, m, p);
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}