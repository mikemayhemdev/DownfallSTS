package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.util.Wiz.applyToEnemy;

public class SufferingPower extends AbstractCollectorPower implements BetterOnApplyPowerPower {
    public static final String NAME = "Suffering";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SufferingPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if (!(target instanceof AbstractPlayer)) {
            if (abstractPower.ID.equals(WeakPower.POWER_ID) || abstractPower.ID.equals(VulnerablePower.POWER_ID)) {
                if (!target.hasPower(ArtifactPower.POWER_ID)) {
                    flash();
                    applyToEnemy((AbstractMonster) target, new DoomPower((AbstractMonster) target, amount)); // NOW fixed
                }
            }
        }
        return true;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}