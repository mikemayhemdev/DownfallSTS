package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FalseCounter extends AbstractChampCard {

    public final static String ID = makeID("FalseCounter");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = -1;

    public FalseCounter() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        if (p.hasPower(CounterPower.POWER_ID)) {
            int x = p.getPower(CounterPower.POWER_ID).amount / magicNumber;
            applyToSelf(new StrengthPower(p, x));
            atb(new RemoveSpecificPowerAction(p, p, CounterPower.POWER_ID));
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}