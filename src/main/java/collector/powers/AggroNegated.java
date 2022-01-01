package collector.powers;

import collector.CollectorMod;
import collector.actions.AddAggroAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AggroNegated extends AbstractPower {
    public static final String POWER_ID = CollectorMod.makeID("AggroNegated");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggered = false;

    public AggroNegated(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        this.loadRegion("anger");
    }

    @Override
    public void atStartOfTurnPostDraw(){
        addToBot(new AddAggroAction(true,amount));
    }
}