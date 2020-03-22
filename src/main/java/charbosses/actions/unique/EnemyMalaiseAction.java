package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import charbosses.ui.EnemyEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnemyMalaiseAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private boolean upgraded = false;
    private AbstractCharBoss c;
    private int energyOnUse = -1;

    public EnemyMalaiseAction(AbstractCharBoss t, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.c = t;
        this.freeToPlayOnce = freeToPlayOnce;// 27
        this.upgraded = upgraded;// 28
        this.duration = Settings.ACTION_DUR_XFAST;// 29
        this.actionType = ActionType.SPECIAL;// 30
        this.energyOnUse = energyOnUse;// 31
    }// 32

    public void update() {
        int effect = EnemyEnergyPanel.totalCount;// 36
        if (this.energyOnUse != -1) {// 37
            effect = this.energyOnUse;// 38
        }

        if (this.upgraded) {// 46
            ++effect;// 47
        }

        if (effect > 0) {// 50
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, c, new StrengthPower(AbstractDungeon.player, -effect), -effect));// 51
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, c, new WeakPower(AbstractDungeon.player, effect, false), effect));// 52
            if (!this.freeToPlayOnce) {// 54
                c.energy.use(EnergyPanel.totalCount);// 55
            }
        }

        this.isDone = true;// 58
    }// 59
}
