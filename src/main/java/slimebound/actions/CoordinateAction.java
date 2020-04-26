//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.powers.PotencyPower;

public class CoordinateAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private int damage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageType damageTypeForTurn;
    private int energyOnUse = -1;
    private int slimesToTrigger = 0;
    private int block;

    public CoordinateAction(AbstractPlayer p, AbstractMonster m, int slimesToTrigger, boolean freeToPlayOnce, int energyOnUse, int block) {
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.slimesToTrigger = slimesToTrigger;
        this.energyOnUse = energyOnUse;
        this.block = block;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {


            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        for (int i = 0; i < effect; i++)
            addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new PotencyPower(p, p, effect), effect));

        for (int i = 0; i < effect; ++i) {
            addToBot(new CommandAction());
        }

        this.isDone = true;
    }
}
