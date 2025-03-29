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
import com.megacrit.cardcrawl.relics.FrozenEye;
import theHexaghost.relics.CandleOfCauterizing;

import static collector.util.Wiz.att;

public class DrawCardFromCollectionAction extends AbstractGameAction {
    public DrawCardFromCollectionAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (!CollectorCollection.combatCollection.isEmpty()) {
            AbstractCard tar = CollectorCollection.combatCollection.getRandomCard(AbstractDungeon.cardRandomRng);
            if(AbstractDungeon.player.hasRelic(FrozenEye.ID)) {
                tar = CollectorCollection.combatCollection.getTopCard();
            }
            CollectorCollection.combatCollection.removeCard(tar);
            AbstractDungeon.player.drawPile.addToTop(tar);
            att(new DrawCardAction(1));
        } else {
            if (AbstractDungeon.player.hasRelic(HolidayCoal.ID)) {
                AbstractDungeon.player.getRelic(HolidayCoal.ID).flash();
                AbstractCard tar = new LuckyWick();
                CardModifierManager.addModifier(tar, new CollectedCardMod());
                AbstractDungeon.player.drawPile.addToTop(tar);
                att(new DrawCardAction(1));
            }
        }
        this.isDone = true;
    }
}