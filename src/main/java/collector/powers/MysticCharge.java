package collector.powers;

import collector.CollectorMod;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class MysticCharge extends TwoAmountPower {
    public static final String POWER_ID = CollectorMod.makeID("MysticCharge");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggered = false;

    public MysticCharge(AbstractCreature owner, int time,int power) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = power;
        this.amount2 = time;
        this.description = DESCRIPTIONS[0];
        this.loadRegion("closeUp");
        isTurnBased = true;
    }

    @Override
    public void atStartOfTurnPostDraw(){
        amount2 -= 1;
        if (amount2 <= 0){
            addToBot(new ApplyPowerAction(owner,owner,new VigorPower(owner,amount),amount));
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        }
    }
}