package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

public class RetractAction extends AbstractGameAction {
    private int number;

    public RetractAction() {
        this(1);
    }

    public RetractAction(int num){
        this.number = num;
    }

    public void update() {
        isDone = true;
      //  if (!HexaMod.renderFlames)
      //      HexaMod.renderFlames = true;
        for(int i = 1; i <= number; i++) {
            GhostflameHelper.retract();
        }
        HexaMod.number_of_times_of_retracts_during_the_combat += number; // for calculating the rare attack's bonus damage
    }
}
