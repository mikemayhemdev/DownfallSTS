package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import guardian.powers.EnergizedGuardianPower;

public class ChargeBattery extends AbstractBronzeCard {
    public final static String ID = makeID("ChargeBattery");

    public ChargeBattery() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new EnergizedBluePower(p, 1));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}
