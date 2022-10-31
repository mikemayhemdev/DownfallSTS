package collector.powers;

import basemod.interfaces.CloneablePowerInterface;
import collector.CollectorMod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GainBlockPower extends AbstractPower implements CloneablePowerInterface, InvisiblePower {

    public static final String POWER_ID = CollectorMod.makeID("GainBlock");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GainBlockPower(final int amount, AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("corruption");
        this.updateDescription();
    }


    @Override
    public void updateDescription() {}

    @Override
    public AbstractPower makeCopy() {
        return new GainBlockPower(amount, owner);
    }
    public void onInitialApplication() {
        addToBot(new GainBlockAction(owner,amount));
        addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }
}