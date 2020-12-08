package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.actions.AbstractXAction;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.LoseEnhanceInTurnsPower;

public class TurnItUpAction extends AbstractXAction {

    private int bonusAmt;

    public TurnItUpAction(int bonusAmt) {
        this.bonusAmt = bonusAmt;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void initialize(int totalAmount) {
        super.initialize(totalAmount);
        this.amount += bonusAmt;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new EnhancePower(5), 5));
        addToTop(new ApplyPowerAction(p, p, new LoseEnhanceInTurnsPower(amount, 5), amount));
        this.isDone = true;
    }
}