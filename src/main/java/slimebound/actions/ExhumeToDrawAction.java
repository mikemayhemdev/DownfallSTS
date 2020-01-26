package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhumeToDrawAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final boolean upgrade;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<AbstractCard> exhumes = new ArrayList();

    public ExhumeToDrawAction(boolean upgrade) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator<AbstractCard> card;
        if (this.duration == Settings.ACTION_DUR_FAST) {



            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            AbstractCard c;
            if (this.p.exhaustPile.size() == 1) {

                if (((AbstractCard) this.p.exhaustPile.group.get(0)).cardID.equals("Recollect")) {
                    this.isDone = true;
                    return;
                }

                c = this.p.exhaustPile.getTopCard();
                c.unfadeOut();
                this.p.drawPile.addToRandomSpot(c);

                this.p.exhaustPile.removeCard(c);
                if ((this.upgrade) && (c.canUpgrade())) {
                    c.upgrade();
                }
                c.unhover();
                c.fadingOut = false;
                this.isDone = true;
                return;
            }


            for (AbstractCard c2 : this.p.exhaustPile.group) {
                c2.stopGlowing();
                c2.unhover();
                c2.unfadeOut();
            }


            if (this.p.exhaustPile.isEmpty()) {
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
            tickDuration();
            return;
        }


        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.drawPile.addToRandomSpot(c);

                this.p.exhaustPile.removeCard(c);
                if ((this.upgrade) && (c.canUpgrade())) {
                    c.upgrade();
                }
                c.unhover();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            //this.p.hand.refreshHandLayout();


            this.p.exhaustPile.group.addAll(this.exhumes);
            this.exhumes.clear();

            for (AbstractCard c : this.p.exhaustPile.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }
        }

        tickDuration();
    }
}


