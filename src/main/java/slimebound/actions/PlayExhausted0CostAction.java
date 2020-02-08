//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public class PlayExhausted0CostAction extends AbstractGameAction {


    public PlayExhausted0CostAction(AbstractCreature target) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;


    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (AbstractDungeon.player.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            AbstractCard card = null;
            int cardsReturned = 0;
            //int healthLoss = exhaustSize;
            CardGroup cardsToReturn = AbstractDungeon.player.exhaustPile;
            cardsToReturn.shuffle();
            List<AbstractCard> cardsToExhaust = new ArrayList<>();

            for (AbstractCard c : cardsToReturn.group) {

                if (c.cost == 0) {

                    c.unfadeOut();
                    c.unhover();
                    c.fadingOut = false;
                    card = c;
                    break;


                    //logger.info("Modify Cost");
                    // c.modifyCostForCombat(-1);


                }
            }
            if (card == null) {

                this.isDone = true;
                return;
            }

            //AbstractDungeon.player.exhaustPile.removeCard(card);


            AbstractDungeon.player.exhaustPile.group.remove(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);
            card.freeToPlayOnce = true;
            //card.exhaustOnUseOnce = this.exhaustCards;
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
            card.target_y = (float) Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            if (!card.canUse(AbstractDungeon.player, (AbstractMonster) this.target)) {

                AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));

            } else {
                card.applyPowers();
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
                AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }

            }

            this.isDone = true;
        }

    }
}
