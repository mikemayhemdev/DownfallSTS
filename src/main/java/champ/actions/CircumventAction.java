//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package champ.actions;

import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CircumventAction extends AbstractGameAction {

    public void update() {
        if (AbstractDungeon.player.hasPower(CounterPower.POWER_ID)) {
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player.getPower(CounterPower.POWER_ID).amount));
        }

        this.isDone = true;
    }
}
