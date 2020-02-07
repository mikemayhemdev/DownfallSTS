package charbosses.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToDiscardEffect;
import charbosses.actions.vfx.cardmanip.EnemyShowCardAndAddToHandEffect;
import charbosses.bosses.AbstractCharBoss;

public class EnemyMakeTempCardInHandAction extends AbstractGameAction
{
    private AbstractCard c;
    private static final float PADDING;
    private boolean isOtherCardInCenter;
    private boolean sameUUID;
    
    public EnemyMakeTempCardInHandAction(final AbstractCard card, final boolean isOtherCardInCenter) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        this.amount = 1;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
        this.isOtherCardInCenter = isOtherCardInCenter;
    }
    
    public EnemyMakeTempCardInHandAction(final AbstractCard card) {
        this(card, 1);
    }
    
    public EnemyMakeTempCardInHandAction(final AbstractCard card, final int amount) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card;
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractCharBoss.boss.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
    }
    
    public EnemyMakeTempCardInHandAction(final AbstractCard card, final int amount, final boolean isOtherCardInCenter) {
        this(card, amount);
        this.isOtherCardInCenter = isOtherCardInCenter;
    }
    
    public EnemyMakeTempCardInHandAction(final AbstractCard card, final boolean isOtherCardInCenter, final boolean sameUUID) {
        this(card, 1);
        this.isOtherCardInCenter = isOtherCardInCenter;
        this.sameUUID = sameUUID;
    }
    
    @Override
    public void update() {
        if (this.amount == 0) {
            this.isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = this.amount;
        if (this.amount + AbstractCharBoss.boss.hand.size() > 10) {
            discardAmount = this.amount + AbstractCharBoss.boss.hand.size() - 10;
            handAmount -= discardAmount;
        }
        this.addToHand(handAmount);
        this.addToDiscard(discardAmount);
        if (this.amount > 0) {
            this.addToTop(new WaitAction(0.8f));
        }
        this.isDone = true;
    }
    
    private void addToHand(final int handAmt) {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                if (handAmt != 1) {
                    break;
                }
                if (this.isOtherCardInCenter) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard()));
                break;
            }
            case 2: {
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                break;
            }
            case 3: {
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 3) {
                    for (int i = 0; i < this.amount; ++i) {
                        AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard()));
                    }
                    break;
                }
                break;
            }
            default: {
                for (int i = 0; i < handAmt; ++i) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToHandEffect(this.makeNewCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f)));
                }
                break;
            }
        }
    }
    
    private void addToDiscard(final int discardAmt) {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                break;
            }
            case 2: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    break;
                }
                break;
            }
            case 3: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 3) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (EnemyMakeTempCardInHandAction.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                break;
            }
            default: {
                for (int i = 0; i < discardAmt; ++i) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.makeNewCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f)));
                }
                break;
            }
        }
    }
    
    private AbstractCard makeNewCard() {
        if (this.sameUUID) {
            return this.c.makeSameInstanceOf();
        }
        return this.c.makeStatEquivalentCopy();
    }
    
    static {
        PADDING = 25.0f * Settings.scale;
    }
}
