package charbosses.powers.cardpowers;


import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import sun.reflect.Reflection;

import java.util.ArrayList;

public class EnemyTheBombPower extends AbstractPower {
    public static final String POWER_ID = "TheBomb";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int damage;
    private static int bombIdOffset;

    private float timer;

    public EnemyTheBombPower(AbstractCreature owner, int turns, int damage) {
        this.name = NAME;
        this.ID = "TheBomb" + bombIdOffset;
        ++bombIdOffset;
        this.owner = owner;
        this.amount = turns;
        this.damage = damage;
        this.updateDescription();
        this.loadRegion("the_bomb");
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        if (this.amount == 1) {
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, this.damage, DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }


    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (this.amount == 1){
            if (this.timer <= 0F){
                ArrayList<AbstractGameEffect> effect2 = (ArrayList<AbstractGameEffect>) ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
                effect2.add(new GainPowerEffect(this));
                this.timer = 1F;
            } else {
                this.timer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void playApplyPowerSfx() {
        //to prevent the 'last turn' warning from pinging audio all the time
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = String.format(DESCRIPTIONS[1], this.damage);
        } else {
            this.description = String.format(DESCRIPTIONS[0], this.amount, this.damage);
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("TheBomb");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
