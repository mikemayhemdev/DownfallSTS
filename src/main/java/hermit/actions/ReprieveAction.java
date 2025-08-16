package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.purple.Vault;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hermit.powers.ReprievePower;

import java.util.Iterator;

public class ReprieveAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private int totalcards = 0;
    int curseTreshold = 0;

    public ReprieveAction(int curseTreshold) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.curseTreshold = curseTreshold;
    }

    public static int countCards(CardGroup varGroup) {
        int count = 0;

        for (AbstractCard c: varGroup.group)
        {
            if (c.color== AbstractCard.CardColor.CURSE)
                count++;
        }

        return count;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (countCards(this.p.drawPile) == 0 && countCards(this.p.discardPile) == 0 && countCards(this.p.hand) == 0) {
                this.isDone = true;
                return;
            }

            for (int i = p.drawPile.group.size(); i > 0; i--) {
                AbstractCard c = this.p.drawPile.group.get(i-1);
                if (c.color == AbstractCard.CardColor.CURSE) {
                    this.p.drawPile.moveToExhaustPile(c);
                    this.totalcards++;
                }
            }

            for (int i = p.discardPile.group.size(); i > 0; i--) {
                AbstractCard c = this.p.discardPile.group.get(i-1);
                if (c.color == AbstractCard.CardColor.CURSE) {
                    this.p.discardPile.moveToExhaustPile(c);
                    this.totalcards++;
                }
            }

            for (int i = p.hand.group.size(); i > 0; i--) {
                AbstractCard c = this.p.hand.group.get(i-1);
                if (c.color == AbstractCard.CardColor.CURSE) {
                    this.p.hand.moveToExhaustPile(c);
                    this.totalcards++;
                }
            }

            if (this.totalcards >= this.curseTreshold)
            {
                this.addToTop(new ApplyPowerAction(p, p, new ReprievePower(p,p, 1), 1));
            }

            totalcards = 0;
            this.isDone = true;
        }
        this.tickDuration();
    }
}