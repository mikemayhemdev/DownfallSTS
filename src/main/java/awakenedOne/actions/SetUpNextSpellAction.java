package awakenedOne.actions;

import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static awakenedOne.AwakenedOneMod.UP_NEXT;
import static awakenedOne.ui.OrbitingSpells.spellCards;

public class SetUpNextSpellAction extends AbstractGameAction {
    public SetUpNextSpellAction() {
    }

    @Override
    public void update() {
        isDone = true;

        boolean setup = true;

        for (AbstractCard c : spellCards) {
            if (c.hasTag(UP_NEXT)) {
                setup = false;
            }
        }

        if (setup) {
            OrbitingSpells.setupnext();
        }
    }
}
