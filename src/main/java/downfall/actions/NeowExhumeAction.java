package downfall.actions;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
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

public class NeowExhumeAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private AbstractPlayer p;

    public NeowExhumeAction() {

        this.p = AbstractDungeon.player;

        setValues(this.p, AbstractDungeon.player, this.amount);

        this.actionType = ActionType.CARD_MANIPULATION;

        this.duration = Settings.ACTION_DUR_FAST;

    }


    public void update() {

        int cardsReturned = 0;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.exhaustPile.isEmpty()) {

                logger.info("Exhaust is empty");
                this.isDone = true;
                return;

            }

            logger.info("Add to hand");
            AbstractCard c = AbstractDungeon.player.exhaustPile.getRandomCard(AbstractDungeon.cardRandomRng);

            this.p.drawPile.addToTop(c);

            c.unfadeOut();
            c.unhover();
            this.p.exhaustPile.removeCard(c);

            c.flash(Color.GOLD.cpy());


            this.isDone = true;

            return;

        }

        tickDuration();
    }
}



