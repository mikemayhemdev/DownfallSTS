package champ.cards;

import champ.powers.CounterPower;
import champ.powers.FalseCounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FalseCounter extends AbstractChampCard {

    public final static String ID = makeID("FalseCounter");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 4;

    public FalseCounter() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new FalseCounterPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}