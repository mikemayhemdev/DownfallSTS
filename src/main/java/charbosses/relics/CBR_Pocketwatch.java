package charbosses.relics;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Pocketwatch;

public class CBR_Pocketwatch extends AbstractCharbossRelic {
    public static final String ID = "Pocketwatch";
    private boolean firstTurn = true;

    public CBR_Pocketwatch() {
        super(new Pocketwatch());
        this.counter = 0;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {

        this.counter = 0;
        this.firstTurn = true;
        this.beginLongPulse();
    }

    @Override
    public void atTurnStartPostDraw() {
        if (this.counter <= 3 && !this.firstTurn) {
            //this.addToBot(new EnemyDrawCardAction(this.owner, 3));
        } else {
            this.firstTurn = false;
        }

        this.counter = 0;
        this.beginLongPulse();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        ++this.counter;
        if (this.counter > 3) {
            this.stopPulse();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Pocketwatch();
    }
}
