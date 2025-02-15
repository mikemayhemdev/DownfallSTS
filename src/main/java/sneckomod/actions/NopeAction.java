package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;

public class NopeAction extends AbstractGameAction {
    private static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(SneckoMod.makeID("Nope")).EXTENDED_DESCRIPTION;
    private AbstractPlayer p;

    public NopeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(EXTENDED_DESCRIPTION[0], 1, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                AbstractCard c = p.hand.getTopCard();
                if (isCardInvalid(c)) {
                    this.isDone = true;
                    return;
                }

                //p.hand.moveToExhaustPile(c);
                AbstractCard card = SneckoMod.getSpecificClassCard(c.color);
                this.addToBot(new MakeTempCardInHandAction(card));
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (isCardInvalid(c)) {
                    continue;
                }

                p.hand.moveToExhaustPile(c);
                AbstractCard card = SneckoMod.getSpecificClassCard(c.color);
                this.addToBot(new MakeTempCardInHandAction(card));
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private boolean isCardInvalid(AbstractCard card) {
        p.hand.moveToExhaustPile(card);
        return card.color == AbstractCard.CardColor.COLORLESS && card.rarity == AbstractCard.CardRarity.SPECIAL
                || card.type == AbstractCard.CardType.STATUS
                || card.type == AbstractCard.CardType.CURSE;
    }
}