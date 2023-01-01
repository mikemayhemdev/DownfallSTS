package awakenedOne.actions;

import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class RefreshSpellsAction extends AbstractGameAction {
    @Override
    public void update() {
        isDone = true;
        OrbitingSpells.refreshSpells();
    }
}
