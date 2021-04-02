package gremlin.actions;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class MakeEchoAction extends AbstractGameAction {
    private static final float DURATION_PER_CARD = 0.35F;
    private AbstractCard c;
    private static final float PADDING = 25.0F * Settings.scale;

    private int discount;

    public MakeEchoAction(AbstractCard card)
    {
        this(card, 1, 0);
    }

    public MakeEchoAction(AbstractCard card, int amount)
    {
        this(card, amount, 0);
    }

    public MakeEchoAction(AbstractCard card, int amount, int discount)
    {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = DURATION_PER_CARD;
        this.c = card;
        this.discount = discount;
    }

    private AbstractCard echoCard(){
        AbstractCard card = this.c.makeStatEquivalentCopy();
        card.name = CardCrawlGame.languagePack.getUIString("Gremlin:MakeEchoAction").TEXT[0] + card.name;
        card.exhaust = true;
        card.isEthereal = true;
        AlwaysRetainField.alwaysRetain.set(card, false);
        card.retain = false;
        if(card.cost >= 0 && this.discount>0)
            card.updateCost(-1*this.discount);
        return card;
    }

    public void update()
    {
        if (this.amount == 0)
        {
            this.isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = this.amount;
        if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE)
        {
            AbstractDungeon.player.createHandIsFullDialog();
            discardAmount = this.amount + AbstractDungeon.player.hand.size() - BaseMod.MAX_HAND_SIZE;
            handAmount -= discardAmount;
        }
        addToHand(handAmount);
        addToDiscard(discardAmount);
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
        }
        this.isDone = true;
    }

    private void addToHand(int handAmt)
    {
        switch (this.amount)
        {
            case 0:
                break;
            case 1:
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard()));
                }
                break;
            case 2:
                if (handAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT / 2.0F));
                }
                else if (handAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                break;
            case 3:
                if (handAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                else if (handAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                else if (handAmt == 3)
                {
                    for (int i = 0; i < this.amount; i++) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard()));
                    }
                }
                break;
            default:
                for (int i = 0; i < handAmt; i++) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(echoCard(),
                            MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F),
                            MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
                }
        }
    }

    private void addToDiscard(int discardAmt)
    {
        switch (this.amount)
        {
            case 0:
                break;
            case 1:
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
                }
                break;
            case 2:
                if (discardAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));
                }
                else if (discardAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));
                }
                break;
            case 3:
                if (discardAmt == 1)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
                }
                else if (discardAmt == 2)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
                }
                else if (discardAmt == 3)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
                }
                break;
            default:
                for (int i = 0; i < discardAmt; i++) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(echoCard(),
                            MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F),
                            MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
                }
        }
    }
}

