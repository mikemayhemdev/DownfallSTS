package collector.actions;

import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import collector.cards.collectibles.LuckyWick;
import collector.relics.HolidayCoal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.att;

public class DrawCardFromCollectionAction extends AbstractGameAction {
    public DrawCardFromCollectionAction() {
        this.actionType = ActionType.SPECIAL;
    }

    //okay so the way this nerf works is that I changed DrawFromCollection's patch to method = "applyStartOfTurnPreDrawCards"
    //that way the player is forced to draw past their collected card to reach their deck
    //unfortunately, a side effect of this is that any time you want to write something that draws from the collected pile,
    //you have to write this code :
    //            att(new DrawCardFromCollectionAction());
    //            if (!CollectorCollection.combatCollection.isEmpty()) {
    //                atb(new DrawCardAction(1));
    //            }
    //with the 1 being replaced by however many times you wrote atb(new DrawCardFromCollectionAction());
    //so basically this isn't really "DrawCardFromCollectionAction", it's more "Place card from Collection on top of Draw Pile."

    @Override
    public void update() {
        if (!CollectorCollection.combatCollection.isEmpty()) {
            AbstractCard tar = CollectorCollection.combatCollection.getTopCard();
            CollectorCollection.combatCollection.removeCard(tar);
            AbstractDungeon.player.drawPile.addToTop(tar);
           // att(new DrawCardAction(1));
        } else {
            if (AbstractDungeon.player.hasRelic(HolidayCoal.ID)) {
                AbstractDungeon.player.getRelic(HolidayCoal.ID).flash();
                AbstractCard tar = new LuckyWick();
                CardModifierManager.addModifier(tar, new CollectedCardMod());
                AbstractDungeon.player.hand.addToTop(tar);
             //   att(new DrawCardAction(1));
            }
        }
        this.isDone = true;
    }
}