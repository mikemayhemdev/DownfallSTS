package awakenedOne.powers;

import awakenedOne.relics.HexxBomb;
import awakenedOne.relics.StrengthBooster;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.relics.CandleOfCauterizing;


//unused

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
            return this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic(StrengthBooster.ID) ? damage * 1.75F : damage * 1.5F;
        }
        return damage;
    }



    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (0 >= this.amount) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            //Hexx Bomb trigger
            if(AbstractDungeon.player.hasRelic(HexxBomb.ID)) {
                AbstractDungeon.player.getRelic(HexxBomb.ID).onTrigger();
            }
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            this.flashWithoutSound();
            this.amount = 0;
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        int num = 50;

        if(AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
        num = num + 25;
        }

        description = DESCRIPTIONS[0] + num + DESCRIPTIONS[1];
    }

    public Color getColor() {return Color.PURPLE;}

}
