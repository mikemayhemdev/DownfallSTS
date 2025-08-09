package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import hermit.util.Wiz;

import java.util.ArrayList;

public class EncyclopediaAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private String text;

    private int amount;

    public EncyclopediaAction(int amount, String text) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.text = text;
        this.amount = amount;
    }

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);


            for (AbstractCard c:generateCardChoices()
                 ) {
                tmp.addToBottom(c);
            }

            tmp.sortAlphabetically(true);
            tmp.sortByRarityPlusStatusCardType(false);

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() <= 2) {
                for (AbstractCard abstractCard : tmp.group) {
                    Wiz.atb(new MakeTempCardInHandAction(abstractCard,1));
                }
                this.isDone = true;
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, 2, text, false);
                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                for (AbstractCard abstractCard : AbstractDungeon.gridSelectScreen.selectedCards) {
                    Wiz.atb(new MakeTempCardInHandAction(abstractCard,1));
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }



    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();

        while(derp.size() != amount) {
            boolean dupe = false;
            AbstractCard tmp = null;
            tmp = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();

            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                tmp.modifyCostForCombat(-2);
                derp.add(tmp);
            }
        }

        return derp;
    }
}
