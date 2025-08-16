package awakenedOne.actions;

import awakenedOne.powers.MoonlitVisionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;


public class MoonlitVisionAction extends AbstractGameAction {
    public int amount;


    public MoonlitVisionAction(int amount) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        isDone = true;
        if (AbstractDungeon.player.hand.size() >= 8) {
            AbstractDungeon.player.getPower(MoonlitVisionPower.POWER_ID).flash();
            atb(new GainEnergyAction(this.amount));
        }
    }
}