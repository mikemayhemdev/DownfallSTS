package collector.actions;

import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import collector.cards.collectibles.LuckyWick;
import collector.relics.HolidayCoal;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.att;

public class DrawAndRemoveMegatherealFromCollectedCardAction extends AbstractGameAction {
    public DrawAndRemoveMegatherealFromCollectedCardAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (!CollectorCollection.combatCollection.isEmpty()) {
            AbstractCard tar = CollectorCollection.combatCollection.getTopCard();
            CollectorCollection.combatCollection.removeCard(tar);
            CardModifierManager.removeModifiersById(tar, CollectedCardMod.ID, true);
            AbstractDungeon.player.drawPile.addToTop(tar);
            att(new DrawCardAction(1, new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        c.superFlash(Color.GOLD.cpy());
                    }
                }
            }));
        } else {
            if (AbstractDungeon.player.hasRelic(HolidayCoal.ID)) {
                AbstractDungeon.player.getRelic(HolidayCoal.ID).flash();
                AbstractCard tar = new LuckyWick();
                AbstractDungeon.player.drawPile.addToTop(tar);
                att(new DrawCardAction(1, new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        for (AbstractCard c : DrawCardAction.drawnCards) {
                            c.superFlash(Color.GOLD.cpy());
                        }
                    }
                }));
            }
        }
        this.isDone = true;
    }
}