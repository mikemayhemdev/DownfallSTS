package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class PerformXAction extends AbstractGameAction {
    protected int baseValue = -1;
    protected boolean freeToPlayOnce = false;

    protected AbstractXAction XAction = null;

    private AbstractPlayer p;

    public PerformXAction(AbstractXAction XAction, AbstractPlayer p, int energyOnUse, boolean freeToPlayOnce) {
        this.baseValue = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
        this.XAction = XAction;
        this.p = p;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.baseValue != -1) {
            effect = this.baseValue;
        }

        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }

        XAction.initialize(effect);
        if (XAction.amount > 0) {
            AbstractDungeon.actionManager.addToTop(XAction);
        }

        if (!this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }
        isDone = true;
    }
}