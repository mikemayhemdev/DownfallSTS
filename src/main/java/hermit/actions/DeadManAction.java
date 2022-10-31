package hermit.actions;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Event;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RarePotionParticleEffect;

import java.util.Iterator;

public class DeadManAction extends AbstractGameAction {
    private AbstractPlayer p;
    private CardType typeToCheck;

    public DeadManAction(int amount) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            Array<CardGroup> groups = new Array();
            for (int i =0;i<7;i++)
            groups.add(new CardGroup(CardGroupType.UNSPECIFIED));


            Iterator var2 = this.p.drawPile.group.iterator();

            AbstractCard card;
            while(var2.hasNext()) {
                card = (AbstractCard)var2.next();
                if (card.rarity == AbstractCard.CardRarity.RARE)
                    groups.get(0).addToTop(card);
                else if (card.rarity == AbstractCard.CardRarity.UNCOMMON)
                    groups.get(1).addToTop(card);
                else if (card.rarity == AbstractCard.CardRarity.COMMON && card.type != CardType.STATUS)
                    groups.get(2).addToTop(card);
                else if (card.rarity == AbstractCard.CardRarity.SPECIAL)
                    groups.get(3).addToTop(card);
                else if (card.rarity == AbstractCard.CardRarity.BASIC)
                    groups.get(4).addToTop(card);
                else if (card.rarity == AbstractCard.CardRarity.CURSE)
                    groups.get(5).addToTop(card);
                else if (card.type == CardType.STATUS)
                    groups.get(6).addToTop(card);
            }

            int group = 0;

            for(int i = 0; i < this.amount; ++i) {
                while (group < 7 && groups.get(group).isEmpty())
                    group++;
                if (group<7) {
                    groups.get(group).shuffle();
                    card = groups.get(group).getBottomCard();
                    groups.get(group).removeCard(card);
                    p.drawPile.removeCard(card);
                    if (this.p.hand.size() == 10) {
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.p.discardPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }

            this.isDone = true;
        }

        this.tickDuration();
    }
}
