package collector.powers;

import basemod.interfaces.CloneablePowerInterface;
import collector.CollectorMod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SoulSnare extends AbstractPower implements CloneablePowerInterface, BetterOnApplyPowerPower {

    public static final String POWER_ID = CollectorMod.makeID("collector:SoulSnare");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SoulSnare(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.loadRegion("constricted");

        this.updateDescription();
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SoulSnare(amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {

        if (CollectorMod.AfflicitonMatch(abstractPower.ID)){
            this.amount += 1;
            if (abstractPower.ID.equals(this.ID)){
                addToBot(new LoseHPAction(owner,owner,this.amount));
            }
        }

        return true;
    }
}