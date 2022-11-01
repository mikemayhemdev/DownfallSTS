package slimebound.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.List;

public class ExhumeLickAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private final AbstractPlayer p;
    private final int count;
    private final boolean isupgraded;

    public ExhumeLickAction(int count, boolean upgraded) {

        this.count = count;

        this.p = AbstractDungeon.player;

        setValues(this.p, AbstractDungeon.player, this.amount);

        this.actionType = ActionType.CARD_MANIPULATION;

        this.duration = Settings.ACTION_DUR_FAST;
        isupgraded = upgraded;

    }


    private void chooseLick(AbstractCard c) {
        if (isupgraded) c.upgrade();
        if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            this.p.discardPile.addToTop(c);
        } else {
            this.p.hand.addToHand(c);
        }
        c.unfadeOut();
        c.unhover();
        c.fadingOut = false;
    }

    public void update() {

        int cardsReturned = 0;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.exhaustPile.isEmpty()) {

                // logger.info("Exhaust is empty");
                this.isDone = true;
                return;

            }

            CardGroup cardsToReturn = AbstractDungeon.player.exhaustPile;
            cardsToReturn.shuffle();
            List<AbstractCard> cardsToExhaust = new ArrayList<>();
            // logger.info("Exhaust size:" + exhaustSize);

            //Prefer nonBuried licks first
            for (AbstractCard c : cardsToReturn.group) {
                if (c.hasTag(SlimeboundMod.LICK) && !(c.hasTag(SlimeboundMod.BURIED))) {
                    //   logger.info("Add to hand");
                    chooseLick(c);
                    cardsReturned++;
                    cardsToExhaust.add(c);
                    if (cardsReturned == count) break;
                }
            }

            //If slots are left, check the Buried licks
            if (cardsReturned < count) {
                for (AbstractCard c : cardsToReturn.group) {
                    if (c.hasTag(SlimeboundMod.LICK) && (c.hasTag(SlimeboundMod.BURIED))) {
                        //   logger.info("Add to hand");
                        chooseLick(c);
                        cardsReturned++;
                        cardsToExhaust.add(c);
                        if (cardsReturned == count) break;
                    }
                }
            }

            for (AbstractCard c : cardsToExhaust) {
                this.p.exhaustPile.removeCard(c);
            }


            //logger.info("Losing HP");
            //AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, healthLoss));

            this.isDone = true;

            return;

        }

        tickDuration();
    }
}



