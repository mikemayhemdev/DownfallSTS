package guardian.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import guardian.GuardianMod;
import guardian.powers.ModeShiftPower;
import guardian.relics.DefensiveModeMoreBlock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class BraceAction extends AbstractGameAction {
    public int braceValue;

    public BraceAction(int value) {
        this.braceValue = value;

        if (AbstractDungeon.player.hasRelic(DefensiveModeMoreBlock.ID)){
            braceValue += 1;
        }

    }


    public void update() {
        if (AbstractDungeon.player.hasPower(ModeShiftPower.POWER_ID)) {
            ((ModeShiftPower) AbstractDungeon.player.getPower(ModeShiftPower.POWER_ID)).onSpecificTrigger(braceValue);

        }

        this.isDone = true;
    }

}



