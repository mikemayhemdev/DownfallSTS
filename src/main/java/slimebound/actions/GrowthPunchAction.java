package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import slimebound.cards.GrowthPunch;

public class GrowthPunchAction extends AbstractGameAction {

    private final GrowthPunch card;
    private final int growth;

    public GrowthPunchAction(GrowthPunch c, int value) {
        this.card = c;
        this.growth = value;
    }

    @Override
    public void update() {
        card.baseDamage += this.growth;
        card.baseBlock += this.growth;
        isDone = true;
    }
}
