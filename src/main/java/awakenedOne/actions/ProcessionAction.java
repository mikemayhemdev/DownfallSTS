package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

import static awakenedOne.util.Wiz.shuffleIn;

public class ProcessionAction extends AbstractGameAction {

    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT;
    }

    private final AbstractPlayer player;
    private int playAmt;

    public ProcessionAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.player.drawPile.isEmpty()) {
                this.isDone = true;
            } else {
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                Iterator var6 = this.player.drawPile.group.iterator();

                while (var6.hasNext()) {
                    AbstractCard c = (AbstractCard) var6.next();
                    temp.addToTop(c);
                }

                temp.sortAlphabetically(true);
                temp.sortByRarityPlusStatusCardType(false);
                AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard) var1.next();
                    AbstractDungeon.player.drawPile.group.remove(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    this.addToTop(new NewQueueCardAction(c, true, false, true));
                    if ((!c.freeToPlay() && !c.freeToPlayOnce && c.costForTurn > 0)) {
                        shuffleIn(new VoidCard(), c.costForTurn);
                    }
                    if (c.cost == -1) {
                            shuffleIn(new VoidCard(), EnergyPanel.totalCount);
//                        if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
//                            shuffleIn(new VoidCard(), 2);
//                        }
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
