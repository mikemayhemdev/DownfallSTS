package collector.powers;

import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NextTurnDrawFromCollectionPower extends AbstractCollectorPower {
    public static final String NAME = "NextTurnDrawFromCollection";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public NextTurnDrawFromCollectionPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }


    public void atStartOfTurn() {
        this.flash();
        for (int i = 0; i < amount; i++) {
            addToBot(new DrawCardFromCollectionAction());
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

}