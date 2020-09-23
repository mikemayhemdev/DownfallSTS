package champ.actions;

import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.actions.AbstractXAction;
import theHexaghost.powers.EnhancePower;

public class LariatAction extends AbstractXAction {
    private int boom;
    private int boom2;

    public LariatAction(int x, int y) {
        boom = x;
        boom2 = y;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < amount; i++) {
            addToTop(new ApplyPowerAction(p, p, new CounterPower(boom), boom));
            addToTop(new GainBlockAction(p, boom2));
        }
        this.isDone = true;
    }
}