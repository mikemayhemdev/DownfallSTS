package collector.powers;

import collector.CollectorCollection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import downfall.monsters.FleeingMerchant;

import static collector.util.Wiz.att;

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
            if (owner.maxHealth * 0.25F <= this.amount) {
                CollectorCollection.collect((AbstractMonster) owner);
            }
            if (owner instanceof Darkling) { // Retain it on case "some weird shit happened"
                owner.halfDead = true;
                FuckThisDarklings();
            }
        }
    }

    private void instakill() {
        if (!instakilled) {
            flash();
            instakilled = true;
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            if (owner.maxHealth * 0.25F <= this.amount) {
                CollectorCollection.collect((AbstractMonster) owner);
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
        String name;
        if (owner != null) {
            name = owner.name;
        } else {
            name = DESCRIPTIONS[4];
        }
        description = DESCRIPTIONS[0] + FontHelper.colorString(name, "y") + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + owner.maxHealth * 0.25F + DESCRIPTIONS[3];
    }
}