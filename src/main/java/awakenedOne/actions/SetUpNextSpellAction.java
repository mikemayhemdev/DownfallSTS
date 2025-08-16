package awakenedOne.actions;

import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class SetUpNextSpellAction extends AbstractGameAction {
    public SetUpNextSpellAction() {
    }

    @Override
    public void update() {
        isDone = true;
        OrbitingSpells.setupnext();
    }
}
