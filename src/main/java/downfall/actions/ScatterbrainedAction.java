package downfall.actions;

import basemod.BaseMod;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.cards.curses.Scatterbrained;

public class ScatterbrainedAction extends AbstractGameAction {
    public ScatterbrainedAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        int amount = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        AbstractCard q = new Scatterbrained();
        CardModifierManager.addModifier(q, new EtherealMod());
        CardModifierManager.addModifier(q, new ExhaustMod());
        addToTop(new MakeTempCardInHandAction(q, amount));
        this.isDone = true;
    }
}
