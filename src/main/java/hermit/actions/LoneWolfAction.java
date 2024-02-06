package hermit.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import hermit.util.Wiz;

public class LoneWolfAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static AbstractPlayer p;
    public static int numDiscarded;
    private static final float DURATION;


    public LoneWolfAction() {
        p = AbstractDungeon.player;
        this.setValues(p, p, 1);
        this.actionType = ActionType.DISCARD;
        this.duration = DURATION;
    }

    public void update() {
        AbstractCard c;
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            int i;
            if (p.hand.size() == 1) {
                this.amount = 1;
                i = 1;

                for(int n = 0; n < i; ++n) {
                    c = p.hand.getTopCard();
                    if (c.cost > 0) {
                        c.setCostForTurn(0);
                        c.superFlash(Color.GOLD.cpy());
                    }
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }


            numDiscarded = this.amount;
            if (p.hand.size() > this.amount) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
            }

            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                p.hand.removeCard(c);

                if (c.cost > 0) {
                    c.setCostForTurn(0);
                    c.superFlash(Color.GOLD.cpy());
                }

                int amt = Wiz.p().hand.size();
                for (int i = 0; i < amt; ++i) {
                    AbstractCard cc = Wiz.p().hand.getTopCard();
                    Wiz.p().hand.moveToDiscardPile(cc);
                    cc.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }

                p.hand.addToTop(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("hermit:LoneWolfAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}