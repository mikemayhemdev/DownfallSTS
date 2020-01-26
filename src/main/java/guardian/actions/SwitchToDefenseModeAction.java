package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import guardian.powers.ConstructModePower;
import guardian.powers.DefenseModePower;


public class SwitchToDefenseModeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public SwitchToDefenseModeAction(AbstractPlayer p) {
        this.p = p;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {


        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DefenseModePower(p),1));
        
        this.isDone = true;
    }
}
