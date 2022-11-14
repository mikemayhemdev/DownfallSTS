package champ.cards;

import champ.powers.UltimateFormNextTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UltimateStance extends AbstractChampCard {

    public final static String ID = makeID("UltimateStance");

    // intellij stuff power, self, rare

    private static final int MAGIC = 1;

    public UltimateStance() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        ultimateStance();
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}