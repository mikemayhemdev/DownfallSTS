package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.powers.BurnPower;
import theHexaghost.powers.CrispyPower;

public class BurnAction extends AbstractGameAction {
    AbstractMonster m;

    public BurnAction(AbstractMonster monster, int flames) {
        amount = flames;
        m = monster;
    }

    public void update() {
        isDone = true;
        int x = amount;
        if (AbstractDungeon.player.hasPower(CrispyPower.POWER_ID)) {
            AbstractPower p = AbstractDungeon.player.getPower(CrispyPower.POWER_ID);
            p.flash();
            x += p.amount;
        }
        addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, x), x));
    }
}
