package collector.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static champ.ChampMod.vigor;

public class NextTurnVigorPower extends AbstractCollectorPower {
    public static final String NAME = "NextTurnVigor";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public NextTurnVigorPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }


    public void atStartOfTurn() {
        this.flash();
        //applyToSelf(new VigorPower(owner, amount));
        vigor(amount);
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}