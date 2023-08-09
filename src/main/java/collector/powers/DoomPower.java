package collector.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.powers.BurnPerTurnPower;
import theHexaghost.powers.LivingBombPower;
import theHexaghost.relics.SoulConsumer;
import theHexaghost.vfx.ExplosionSmallEffectGreen;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.isAfflicted;

public class DoomPower extends AbstractCollectorPower implements HealthBarRenderPower {
    public static final String NAME = "Doom";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    // private boolean instakilled = false;

    public DoomPower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
        priority = 99;
    }

    @Override
    public int getHealthBarAmount() {
            return amount;
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            explode();
        }
    }// 70

    public void explode(){
        this.flashWithoutSound();

        if (isAfflicted((AbstractMonster)this.owner)){

        } else {

            if (this.owner.hasPower(DemisePower.POWER_ID)){
                atb(new ReducePowerAction(this.owner, this.owner, this.owner.getPower(DemisePower.POWER_ID), 1));
            } else {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }

        this.addToBot(new LoseHPAction(owner, owner, amount, AbstractGameAction.AttackEffect.FIRE));



    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public Color getColor() {
        return Color.PURPLE.cpy();
    }
}