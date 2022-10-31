package collector.powers;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class FalteringFocus extends AbstractPower {
    public static final String POWER_ID = CollectorMod.makeID("FalteringFocus");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggered = false;

    public FalteringFocus(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.description = DESCRIPTIONS[0];
        this.loadRegion("mantra");
    }

    @Override
    public void atStartOfTurnPostDraw(){
            addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }
}