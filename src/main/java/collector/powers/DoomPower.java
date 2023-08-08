package collector.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static collector.util.Wiz.atb;

public class DoomPower extends AbstractCollectorTwoAmountPower implements HealthBarRenderPower {
    public static final String NAME = "Doom";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    // private boolean instakilled = false;

    public DoomPower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
        priority = 99;
    }

    /*
    @Override
    public void onInitialApplication() {
      //  checkInstakill();
      //  checkMerchant();
    }

     */
    /*

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {

        if (owner.currentHealth - damageAmount <= amount) {
            instakill();
        }

        return damageAmount;
    }

     */
/*
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkMerchant();
        checkInstakill();
    }

 */

    /*
    private void checkInstakill() {
        if (this.owner.currentHealth <= this.amount && owner.currentHealth > 0) {
            instakill();
        }
    }

    private void checkMerchant() {
        if (owner.id.equals(FleeingMerchant.ID)) {
            if (amount > FleeingMerchant.CURRENT_DOOM) {
                FleeingMerchant.CURRENT_DOOM = amount;
            }
        }
    }

     */

    /*
    @Override
    public void onDeath() {
        if (!instakilled) {
            flash();
            instakilled = true;
            if (!owner.hasPower(MinionPower.POWER_ID)) {
                CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
                EssenceSystem.changeEssence(getEssenceAmount());
            }
        }
        if (owner instanceof Darkling) { // Retain it on case "some weird shit happened"
            owner.halfDead = true;
            FuckThisDarklings();
        }
    }

    private void instakill() {
        if (!instakilled) {
            flash();
            instakilled = true;
            if (!owner.hasPower(MinionPower.POWER_ID)) {
                CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
                EssenceSystem.changeEssence(getEssenceAmount());
            }
            att(new InstantKillAction(owner));
            if (!(owner instanceof AwakenedOne) && !(owner instanceof Darkling)) // All we were need this if
                att(new HideHealthBarAction(owner));
        }
    }

    private void FuckThisDarklings() {
        boolean allIsDead = true;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            if (m.id.equals(Darkling.ID) && !m.halfDead)
                allIsDead = false;

        if (allIsDead) {
            AbstractDungeon.getCurrRoom().cannotLose = false;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                m.die();
        }
    }


    private int getEssenceAmount() {
        if (owner instanceof AbstractMonster) {
            switch (((AbstractMonster) owner).type) {
                case NORMAL:
                    return 1;
                case ELITE:
                    return 2;
                case BOSS:
                    return 3;
            }
        }
        return 1;
    }

     */

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            this.addToBot(new LoseHPAction(this.owner, this.source, getTrueHPLossValue()));
            atb(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    private int getTrueHPLossValue() {
        int otherDebuffCount = 0;
        for (AbstractPower p : this.owner.powers) {
            if (p.type == PowerType.DEBUFF) {
                if (p != this) {
                    otherDebuffCount++;
                }
            }
        }
        return Math.round(this.amount * (1 + (0.25F * otherDebuffCount)));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }

    @Override
    public int getHealthBarAmount() {
        int result = (int) Math.floor(getTrueHPLossValue());
        this.amount2 = result;
        updateDescription();
        return result;
    }

    @Override
    public Color getColor() {
        return Color.PURPLE.cpy();
    }
}