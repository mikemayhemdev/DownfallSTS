package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hermit.util.Wiz;

public class DeadOrAliveAction extends AbstractGameAction {
    private final DamageInfo info;
    private final AbstractPlayer p;
    private final int energyOnUse;
    private final boolean freeToPlayOnce;

    public DeadOrAliveAction(AbstractPlayer p, AbstractCreature target, DamageInfo info, boolean freeToPlayOnce, int energyOnUse) {
        this.info = info;
        this.target = target;
        this.source = p;
        this.duration = 0.1F;
        this.energyOnUse = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
        this.p = p;

    }

    public void update() {
        if (this.duration == 0.1F && this.target != null && !(this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead)
        {
            int num = EnergyPanel.totalCount;
            if (this.energyOnUse != -1) {
                num = this.energyOnUse;
            }

            if (p.hasRelic("Chemical X")) {
                num += 2;
                p.getRelic("Chemical X").flash();
            }

            for (int i = 0; i < num; i++) {
                Wiz.atb(new DeadOrAliveActionAction(target,info));
            }

            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }

            this.isDone = true;
        }
    }
}
