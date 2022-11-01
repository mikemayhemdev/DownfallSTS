package guardian.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.powers.ModeShiftPower;
import guardian.relics.DefensiveModeMoreBlock;


public class BraceAction extends AbstractGameAction {
    public int braceValue;

    public BraceAction(int value) {
        this.braceValue = value;

        if (AbstractDungeon.player.hasRelic(DefensiveModeMoreBlock.ID)) {
            braceValue += 1;
        }

    }


    public void update() {
        if (AbstractDungeon.player.hasPower(ModeShiftPower.POWER_ID)) {
            ((ModeShiftPower) AbstractDungeon.player.getPower(ModeShiftPower.POWER_ID)).onSpecificTrigger(braceValue);

        }

        this.isDone = true;
    }

}



