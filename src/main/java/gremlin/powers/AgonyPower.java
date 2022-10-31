package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gremlin.GremlinMod;

public class AgonyPower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("Agony");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/agony.png"));
    private boolean justApplied = false;

    public AgonyPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;

        if (isSourceMonster) {
            this.justApplied = true;
        }

        this.img = IMG;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }

        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = strings.DESCRIPTIONS[0] + 80 + strings.DESCRIPTIONS[1] + this.amount + strings.DESCRIPTIONS[2];
        } else {
            this.description = strings.DESCRIPTIONS[0] + 80 + strings.DESCRIPTIONS[1] + this.amount + strings.DESCRIPTIONS[3];
        }

    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return  damage * 0.2F;
        } else {
            return damage;
        }
    }
}
