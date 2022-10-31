package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DisableEndTurnButtonAction extends AbstractGameAction
{
    public DisableEndTurnButtonAction() {
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            AbstractDungeon.overlayMenu.endTurnButton.disable();
        }
        this.tickDuration();
    }
}
