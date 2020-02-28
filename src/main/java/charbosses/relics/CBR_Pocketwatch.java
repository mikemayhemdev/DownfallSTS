package charbosses.relics;

import charbosses.actions.common.EnemyDrawCardAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.relics.Pocketwatch;

public class CBR_Pocketwatch extends AbstractCharbossRelic {
    public static final String ID = "Pocketwatch";
    private boolean firstTurn = true;

    public CBR_Pocketwatch() {
        super(new Pocketwatch());
        this.counter = 0;
        this.tier = RelicTier.BOSS;
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
            this.addToBot(new EnemyDrawCardAction(this.owner, 3));
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
