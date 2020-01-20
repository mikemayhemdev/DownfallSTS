package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ExtinguishAction extends AbstractGameAction {
    public AbstractGhostflame cuteAnimeGirl;

    public ExtinguishAction(AbstractGhostflame flame) {
        cuteAnimeGirl = flame;
    }

    public void update() {
        isDone = true;
        cuteAnimeGirl.extinguish();
    }
}
