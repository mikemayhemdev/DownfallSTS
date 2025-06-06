package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.actions.AceOfWandsAction;

public class FeathersinksPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = FeathersinksPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public FeathersinksPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == EnemyHexedPower.POWER_ID && source == this.owner && target != this.owner && !target.hasPower("Artifact")) {
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));
            }
        }


    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}