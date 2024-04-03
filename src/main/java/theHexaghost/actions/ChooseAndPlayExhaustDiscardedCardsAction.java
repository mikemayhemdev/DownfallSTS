package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.cards.GhostOfSpireFuture;
import theHexaghost.cards.Premonition;

import java.util.ArrayList;
import java.util.Iterator;

public class ChooseAndPlayExhaustDiscardedCardsAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;

    public ChooseAndPlayExhaustDiscardedCardsAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.hand.isEmpty() && this.numberOfCards > 0) {
                if (this.player.hand.size() <= this.numberOfCards ) {
                    ArrayList<AbstractCard> cardsToPlay = new ArrayList();
                    Iterator var5 = this.player.discardPile.group.iterator();

                    AbstractCard c;
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        cardsToPlay.add(c);
                    }

                    var5 = cardsToPlay.iterator();

                    while(var5.hasNext()) {
                        c = (AbstractCard) var5.next();
                        this.player.hand.removeCard(c);
                        AbstractDungeon.getCurrRoom().souls.remove(c);
                        c.exhaustOnUseOnce = true;
                        c.current_y = -200.0F * Settings.scale;
                        c.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                        c.target_y = (float)Settings.HEIGHT / 2.0F;
                        c.targetAngle = 0.0F;
                        c.lighten(false);
                        c.drawScale = 0.12F;
                        c.targetDrawScale = 0.75F;
                        AbstractDungeon.player.limbo.group.add(c);
                        AbstractMonster m = AbstractDungeon.getRandomMonster();
                        if (m == null) continue;
                        c.applyPowers();
                        c.calculateCardDamage(m);
                        this.addToTop(new NewQueueCardAction(c, m, false, true));
                        this.addToTop(new UnlimboAction(c));
                        if (!Settings.FAST_MODE) {
                            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                        } else {
                            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                        }
                    }

                    this.isDone = true;
                } else {
                    if(numberOfCards == 1){
                        AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, TEXT[0], false);
                        this.tickDuration();
                    }else {
                        AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                        this.tickDuration();
                    }
                }
            } else {
                this.isDone = true;
            }
        } else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard) var1.next();
                    this.player.hand.removeCard(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    c.exhaustOnUseOnce = true;
                    c.current_y = -200.0F * Settings.scale;
                    c.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    c.target_y = (float)Settings.HEIGHT / 2.0F;
                    c.targetAngle = 0.0F;
                    c.lighten(false);
                    c.drawScale = 0.12F;
                    c.targetDrawScale = 0.75F;
                    AbstractDungeon.player.limbo.group.add(c);
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m == null) continue;
                    c.applyPowers();
                    c.calculateCardDamage(m);
                    this.addToTop(new NewQueueCardAction(c, m, false, true));
                    this.addToTop(new UnlimboAction(c));
                    if (!Settings.FAST_MODE) {
                        this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            this.tickDuration();
        }
    }

    static {
        TEXT= CardCrawlGame.languagePack.getCardStrings(GhostOfSpireFuture.ID).EXTENDED_DESCRIPTION;
    }
}
