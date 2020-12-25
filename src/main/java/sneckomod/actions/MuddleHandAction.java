package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MuddleHandAction extends AbstractGameAction {
    private AbstractPlayer p;

    private boolean maxRangeModifier;

    public MuddleHandAction() {
        this(false);
    }

    public MuddleHandAction(boolean maxRangeMod) {
        this.actionType = ActionType.CARD_MANIPULATION;// 14
        this.p = AbstractDungeon.player;// 15
        this.duration = Settings.ACTION_DUR_FAST;// 16
        this.maxRangeModifier = maxRangeMod;
    }// 17

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 21

            for (AbstractCard card : this.p.hand.group) {
                addToTop(new MuddleAction(card, maxRangeModifier));
            }

            this.isDone = true;// 33
        } else {
            this.tickDuration();// 38
        }
    }// 34 39
}
