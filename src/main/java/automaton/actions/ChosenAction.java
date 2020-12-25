//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package automaton.actions;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;

public class ChosenAction extends AbstractGameAction {
    private int miscIncrease;
    private UUID uuid;

    public ChosenAction(UUID targetUUID, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
    }

    public void update() {



        this.isDone = true;
    }
}
