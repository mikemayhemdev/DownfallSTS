package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.vfx.MyOrb;

public class GreenFlameAction extends AbstractGameAction {

    private MyOrb orb;
    private AbstractGhostflame gf;

    public GreenFlameAction(MyOrb orb, AbstractGhostflame flame) {
        this.orb = orb;
        this.gf = flame;
    }

    public void update() {
        isDone = true;
        if(this.gf.charged) orb.charge(); // prevent visual bug due to conflicts between code execution order and game action order, without this it can light when not charged
    }
}
