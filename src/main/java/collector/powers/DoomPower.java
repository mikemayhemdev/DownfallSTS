package collector.powers;

import collector.CollectorCollection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.monsters.FleeingMerchant;

import static collector.util.Wiz.atb;

public class DoomPower extends AbstractCollectorPower implements HealthBarRenderPower {
    public static final String NAME = "Doom";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private boolean instakilled = false;

    public DoomPower(AbstractMonster target, int amount) {
        super(NAME, TYPE, TURN_BASED, target, null, amount);
        priority = 99;
    }

    @Override
    public void onInitialApplication() {
        checkInstakill();
        checkMerchant();
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {

        if (owner.currentHealth - damageAmount <= amount) {
            instakill();
        }

        return damageAmount;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkMerchant();
        checkInstakill();
    }

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

    @Override
    public void onDeath() {
        if (!instakilled) {
            flash();
            instakilled = true;
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            CollectorCollection.collect((AbstractMonster) owner);
        }
    }

    private void instakill() {
        if (!instakilled) {
            flash();
            instakilled = true;
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            CollectorCollection.collect((AbstractMonster) owner);
            atb(new HideHealthBarAction(owner));
            atb(new InstantKillAction(owner));
        }
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return Color.PURPLE.cpy();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + FontHelper.colorString(owner.name, "y") + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}