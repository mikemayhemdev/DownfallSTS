package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ChargeAction extends AbstractGameAction {
    public AbstractGhostflame cuteAnimeGirl;

    public ChargeAction(AbstractGhostflame flame) {
        cuteAnimeGirl = flame;
    }

    public void update() {
        isDone = true;
        cuteAnimeGirl.charge();
    }
}
