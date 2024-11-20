package guardian.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
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

        if (Settings.FAST_MODE) {
            this.startDuration = 0.1F;
        } else {
            this.startDuration = Settings.ACTION_DUR_FASTER;
        }
        this.duration = this.startDuration;
        this.actionType = ActionType.SPECIAL;
    }


    public void update() {
        if (AbstractDungeon.player.hasPower(ModeShiftPower.POWER_ID)) {
            ((ModeShiftPower) AbstractDungeon.player.getPower(ModeShiftPower.POWER_ID)).onSpecificTrigger(braceValue);
        } else {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.player.hasPower(ModeShiftPower.POWER_ID)) {
                        ((ModeShiftPower) AbstractDungeon.player.getPower(ModeShiftPower.POWER_ID)).onSpecificTrigger(braceValue);
                    }
                    this.isDone = true;
                }
            });
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new ModeShiftPower(AbstractDungeon.player, AbstractDungeon.player, 20), 20));
        }
        this.isDone = true;
    }
}