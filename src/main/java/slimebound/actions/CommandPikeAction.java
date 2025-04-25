package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;

public class CommandPikeAction extends AbstractGameAction {

    public void update() {
        isDone = true;
        AbstractOrb oldestOrb = SlimeboundMod.getLeadingSlime();
       //TODO
        //TODO minion master enchantment
    }
}

