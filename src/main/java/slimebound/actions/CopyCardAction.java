package slimebound.actions;
/*     */
/*     */

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class CopyCardAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
        /*     */ {
    /*  16 */   private static final com.megacrit.cardcrawl.localization.UIStrings uiStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getUIString("DualWieldAction");
    /*  17 */   public static final String[] TEXT = uiStrings.TEXT;
    /*     */
    /*     */   private static final float DURATION_PER_CARD = 0.25F;
    /*     */   private AbstractPlayer p;
    /*  21 */   private int dupeAmount = 1;
    /*  22 */   private ArrayList<AbstractCard> cannotDuplicate = new ArrayList();

    /*     */
    /*     */
    public CopyCardAction(AbstractCreature source)
    /*     */ {
        /*  26 */
        setValues(AbstractDungeon.player, source);
        /*  27 */
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
        /*  28 */
        this.duration = 0.25F;
        /*  29 */
        this.p = AbstractDungeon.player;
        /*  30 */
        this.dupeAmount = 1;
        /*     */
    }

    /*     */
    /*     */
    public void update()
    /*     */ {
        /*     */
        int i;
        /*  36 */
        if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST)
            /*     */ {
            /*  38 */
            for (AbstractCard c : this.p.hand.group) {
                /*  39 */
                if (!isDualWieldable(c)) {
                    /*  40 */
                    this.cannotDuplicate.add(c);
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /*  45 */
            if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                /*  46 */
                this.isDone = true;
                /*  47 */
                return;
                /*     */
            }
            /*     */
            /*  50 */
            if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                /*  51 */
                for (AbstractCard c : this.p.hand.group) {
                    /*  52 */
                    if (isDualWieldable(c)) {
                        /*  53 */
                        for (i = 0; i < this.dupeAmount; i++) {
                            /*  54 */
                            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c
/*  55 */.makeStatEquivalentCopy()));
                            /*     */
                        }
                        /*  57 */
                        this.isDone = true;
                        /*  58 */
                        return;
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /*  65 */
            this.p.hand.group.removeAll(this.cannotDuplicate);
            /*     */
            /*  67 */
            if (this.p.hand.group.size() > 1) {
                /*  68 */
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                /*  69 */
                tickDuration();
                /*  70 */
                return;
            }
            /*  71 */
            if (this.p.hand.group.size() == 1) {
                /*  72 */
                for (i = 0; i < this.dupeAmount; i++) {
                    /*  73 */
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(this.p.hand
/*  74 */.getTopCard().makeStatEquivalentCopy()));
                    /*     */
                }
                /*  76 */
                returnCards();
                /*  77 */
                this.isDone = true;
                /*     */
            }
            /*     */
        }
        /*     */
        /*     */
        /*  82 */
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            /*  83 */
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                /*  84 */
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                /*  85 */
                for (i = 0; i < this.dupeAmount; i++) {
                    /*  86 */
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                    /*     */
                }
                /*     */
            }
            /*     */
            /*  90 */
            returnCards();
            /*     */
            /*  92 */
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            /*  93 */
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            /*  94 */
            this.isDone = true;
            /*     */
        }
        /*     */
        /*  97 */
        tickDuration();
        /*     */
    }

    /*     */
    /*     */
    private void returnCards() {
        /* 101 */
        for (AbstractCard c : this.cannotDuplicate) {
            /* 102 */
            this.p.hand.addToTop(c);
            /*     */
        }
        /* 104 */
        this.p.hand.refreshHandLayout();
        /*     */
    }

    /*     */
    /*     */
    private boolean isDualWieldable(AbstractCard card) {
        /* 108 */
        return true;
        /*     */
    }
    /*     */
}

