package awakenedOne.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.powers.ToxicPersonalityPower;
import sneckomod.powers.VenomDebuff;

public class UltimateHexDebuff extends AbstractAwakenedPower implements HealthBarRenderPower, OnReceivePowerPower {
    // intellij stuff buff
    public static final String NAME = UltimateHexDebuff.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public UltimateHexDebuff(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        this.loadRegion("hex");
        this.amount = 1;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EnemyHexedPower.POWER_ID));
        if (power.ID == EnemyHexedPower.POWER_ID) {
            return false;
        }
        return true;
    }


    @Override
    public int getHealthBarAmount() {
        return 999999;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 2;
        }
        return damage;
    }



    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (0 >= this.amount) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            this.flashWithoutSound();
            this.amount = 0;
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EnemyHexedPower.POWER_ID));
        description = DESCRIPTIONS[0];
    }

    public Color getColor() {return Color.PURPLE;}

}
