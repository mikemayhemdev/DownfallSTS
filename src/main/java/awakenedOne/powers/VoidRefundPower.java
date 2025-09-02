package awakenedOne.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

import static awakenedOne.util.Wiz.att;

public class VoidRefundPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = VoidRefundPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public VoidRefundPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, null, amount);
        updateDescription();
    }


    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        
        if (Settings.FAST_MODE) {
            this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.0F));
        } else {
            this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.3F));
        }

        if (!AbstractDungeon.actionManager.turnHasEnded) {
            att(new GainEnergyAction(this.amount));
        }

        if (AbstractDungeon.actionManager.turnHasEnded) {
            att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedBluePower(AbstractDungeon.player, this.amount), this.amount));
        }

        // atb(new DrawCardAction(AbstractDungeon.player, this.amount));
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        }

        if (this.amount != 1) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}