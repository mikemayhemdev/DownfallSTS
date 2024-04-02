package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cards.OctoChoiceCard;

public class PredictAndPlayCardAction extends AbstractGameAction {

    AbstractCard.CardType predicted_card_type;

    public PredictAndPlayCardAction(OctoChoiceCard c) {
        this.predicted_card_type = c.type;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        isDone = true;
        if( !AbstractDungeon.player.drawPile.isEmpty() ){

            CardGroup cards_to_be_played = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for( AbstractCard card: AbstractDungeon.player.drawPile.group ){
                if(card.type == predicted_card_type)  cards_to_be_played.group.add(card);
            }

            AbstractCard card_to_play = cards_to_be_played.getRandomCard(AbstractDungeon.cardRng);
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            AbstractDungeon.player.drawPile.removeCard(card_to_play);
            AbstractDungeon.getCurrRoom().souls.remove(card_to_play);
            if (m == null) return;
            card_to_play.applyPowers();
            card_to_play.calculateCardDamage(m);
            this.addToBot(new NewQueueCardAction(card_to_play, m, false, true));
//                AbstractDungeon.player.drawPile.moveToDiscardPile(card_to_play);

        }
    }

}
