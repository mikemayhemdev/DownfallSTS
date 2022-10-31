package gremlin.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ModifiedLoseStrengthPower extends AbstractPower
{
    public static final String POWER_ID = "Gremlin:Flex";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public ModifiedLoseStrengthPower(AbstractCreature owner, int newAmount) {
        this.name = ModifiedLoseStrengthPower.NAME;
        this.ID = "Flex";
        this.owner = owner;
        this.amount = newAmount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("flex");
    }

    @Override
    public void updateDescription() {
        this.description = ModifiedLoseStrengthPower.DESCRIPTIONS[0] + this.amount + ModifiedLoseStrengthPower.DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if(isPlayer) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Gremlin:Flex");
        NAME = ModifiedLoseStrengthPower.powerStrings.NAME;
        DESCRIPTIONS = ModifiedLoseStrengthPower.powerStrings.DESCRIPTIONS;
    }
}