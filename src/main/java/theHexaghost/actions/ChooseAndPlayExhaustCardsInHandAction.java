package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.cards.GhostOfSpireFuture;

import java.util.ArrayList;
import java.util.Iterator;
@Deprecated
public class ChooseAndPlayExhaustCardsInHandAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;

    public ChooseAndPlayExhaustCardsInHandAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.hand.isEmpty() && this.numberOfCards > 0) {
                if (this.player.hand.size() <= this.numberOfCards ) {
                    ArrayList<AbstractCard> cardsToPlay = new ArrayList<>();
                    Iterator<AbstractCard> var5 = this.player.hand.group.iterator();

                    AbstractCard c;
                    while(var5.hasNext()) {
                        c = var5.next();
                        cardsToPlay.add(c);
                    }

                    var5 = cardsToPlay.iterator();

                    while(var5.hasNext()) {
                        c = var5.next();
                        c.exhaust = true;
                        this.player.hand.group.remove(c);
                        AbstractDungeon.getCurrRoom().souls.remove(c);
//                        AbstractDungeon.player.limbo.group.add(c);
                        AbstractMonster m = AbstractDungeon.getRandomMonster();
                        if (m == null) continue;
                        c.applyPowers();
                        c.calculateCardDamage(m);
                        this.addToBot(new NewQueueCardAction(c, m, false, true));
//                        this.addToBot(new UnlimboAction(c));
                    }

                    this.isDone = true;
                } else {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.numberOfCards, false, false);
                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard) var1.next();
                    c.exhaust = true;
                    this.player.hand.group.remove(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
//                    AbstractDungeon.player.limbo.group.add(c);
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m == null) continue;
                    c.applyPowers();
                    c.calculateCardDamage(m);
                    this.addToBot(new NewQueueCardAction(c, m, false, true));
//                    this.addToBot(new UnlimboAction(c));
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
            this.tickDuration();
        }
    }

    static {
        TEXT= CardCrawlGame.languagePack.getCardStrings(GhostOfSpireFuture.ID).EXTENDED_DESCRIPTION;
    }
}
