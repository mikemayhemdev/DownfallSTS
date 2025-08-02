package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.*;


public class TriggerPrepareEffectsAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractPlayer p;

    public TriggerPrepareEffectsAction(AbstractPlayer p) {
        this.p = p;


    }


    public void update() {
        if (p.hasPower(NextTurnGainSlimeCrush.POWER_ID)){
            p.getPower(NextTurnGainSlimeCrush.POWER_ID).atStartOfTurn();
        }
        if (p.hasPower(NextTurnBlockPower.POWER_ID)){
            p.getPower(NextTurnBlockPower.POWER_ID).atStartOfTurn();
        }
        if (p.hasPower(EnergizedSlimeboundPower.POWER_ID)){
            p.getPower(EnergizedSlimeboundPower.POWER_ID).onEnergyRecharge();
        }
        if (p.hasPower(NextTurnGainStrengthPower.POWER_ID)){
            p.getPower(NextTurnGainStrengthPower.POWER_ID).atStartOfTurn();
        }
        if (p.hasPower(WindupPower.POWER_ID)){
            p.getPower(WindupPower.POWER_ID).atStartOfTurn();
        }
        if (p.hasPower(DrawCardNextTurnPower.POWER_ID)){
            p.getPower(DrawCardNextTurnPower.POWER_ID).atStartOfTurnPostDraw();
        }
        if (p.hasPower(NewPlanPower.POWER_ID)){
            p.getPower(NewPlanPower.POWER_ID).atStartOfTurn();
        }


        this.isDone = true;
    }

}



