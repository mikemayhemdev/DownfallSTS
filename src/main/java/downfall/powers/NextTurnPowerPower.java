package downfall.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.downfallMod;

import static champ.ChampMod.vigor;

public class NextTurnPowerPower extends AbstractPower {
    private AbstractPower powerToGain;
    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(downfallMod.makeID("NextTurnPowerPower"));

    public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
        super();
        this.ID = "Next Turn " + powerToGrant.ID; // not need localization, just so that separate powers don't stack
        this.name = powerStrings.NAME + powerToGrant.name;
        this.owner = owner;
        this.type = powerToGrant.type;
        this.isTurnBased = true;
        this.amount = powerToGrant.amount;
        this.img = powerToGrant.img;
        this.region48 = powerToGrant.region48;
        this.region128 = powerToGrant.region128;
        this.powerToGain = powerToGrant;
        updateDescription();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.GREEN.cpy());
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        powerToGain.amount += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        if (powerToGain.ID != VigorPower.POWER_ID) {
            addToBot(new ApplyPowerAction(owner, owner, powerToGain, powerToGain.amount));
        }
        if (powerToGain.ID == VigorPower.POWER_ID) {
            vigor(amount);
        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToGain == null) {
            description = powerStrings.DESCRIPTIONS[0];
        } else {
            description = powerStrings.DESCRIPTIONS[1] + powerToGain.amount + powerStrings.DESCRIPTIONS[2] + powerToGain.name + powerStrings.DESCRIPTIONS[3];
        }
    }
}
