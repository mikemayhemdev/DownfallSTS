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
public class EnemyHexedPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = UltimateHexDebuff.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EnemyHexedPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        this.loadRegion("hex");
    }


    public float atDamageReceive(float damage, DamageInfo.DamageType type) {

        float damagereturn = 1.0f;
        damagereturn = (damagereturn + (0.2f * amount * damagereturn));


        if(AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            damagereturn = (damagereturn + (0.3f * amount * damagereturn));
        }

        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * damagereturn;
        }

        return damage;
    }

//    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
//        if (0 >= this.amount) {
//            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
//            //Hexx Bomb trigger
//            if(AbstractDungeon.player.hasRelic(HexxBomb.ID)) {
//                AbstractDungeon.player.getRelic(HexxBomb.ID).onTrigger();
//            }
//        }
//    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            this.flashWithoutSound();
            if (this.amount != 0) {
//                //Hexx Bomb trigger
//                if(AbstractDungeon.player.hasRelic(HexxBomb.ID)) {
//                    AbstractDungeon.player.getRelic(HexxBomb.ID).onTrigger();
//                }
            }
            this.amount = 0;
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, EnemyHexedPower.POWER_ID));
        int num = 20*amount;

        if(AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            num = 30*amount;
        }

        description = DESCRIPTIONS[0] + num + DESCRIPTIONS[1];
    }

}
