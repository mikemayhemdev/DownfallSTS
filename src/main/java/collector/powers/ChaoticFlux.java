package collector.powers;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChaoticFlux extends AbstractPower {
    public static final String POWER_ID = CollectorMod.makeID("ChaoticFlux");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean triggered = false;

    public ChaoticFlux(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        this.loadRegion("loop");
    }

    @Override
    public void atStartOfTurnPostDraw(){
        for (int i = 0; i < this.amount; ++i) {
            addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
    }
}