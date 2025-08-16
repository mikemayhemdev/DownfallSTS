package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EyeOfTheStormAction extends AbstractGameAction {

    private final AbstractCard cl;
    private final AbstractPlayer pl;
    private final int c_cost;

    public EyeOfTheStormAction(AbstractPlayer p, AbstractCard c, int cost)
    {
        this.pl = p;
        this.cl = c;
        this.c_cost = cost;
        this.duration = 0.5F;
    }

    public void update() {

        addToTop(new GainEnergyAction(Math.max((this.pl.energy.energyMaster-EnergyPanel.totalCount),0)));
        this.isDone = true;
    }
}
