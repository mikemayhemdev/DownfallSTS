package slimebound.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
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

public class OverexertionAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final boolean upgrade;

    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());


    public OverexertionAction(boolean upgrade) {

        this.upgrade = upgrade;

        this.p = AbstractDungeon.player;

        setValues(this.p, AbstractDungeon.player, this.amount);

        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;

        this.duration = Settings.ACTION_DUR_FAST;

    }


    public void update() {


        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.exhaustPile.isEmpty()) {

                logger.info("Exhaust is empty");
                this.isDone = true;
                return;

            }

            int exhaustSize = p.exhaustPile.size();
            //int healthLoss = exhaustSize;
            CardGroup cardsToReturn = AbstractDungeon.player.exhaustPile;
            List<AbstractCard> cardsToExhaust = new ArrayList<>();

            logger.info("Exhaust size:" + exhaustSize);

            for (AbstractCard c : cardsToReturn.group) {

                if (c.cost == 0) {


                    logger.info("Add to hand");
                    logger.info("Add to hand");
                    if (p.hand.size() >= BaseMod.MAX_HAND_SIZE){
                        this.p.discardPile.addToTop(c);
                    } else {
                        this.p.hand.addToHand(c);
                    }
                    cardsToExhaust.add(c);
                    c.unfadeOut();
                    c.unhover();
                    c.fadingOut = false;



            }


            }
            for (AbstractCard c : cardsToExhaust) {
                this.p.exhaustPile.removeCard(c);
            }


            logger.info("Losing HP");
            //AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, healthLoss));

            this.isDone = true;

            return;

        }

        tickDuration();
    }
}



