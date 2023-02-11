package charbosses.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StoneCalendar;

public class CBR_StoneCalendar extends AbstractCharbossRelic {
    public static final String ID = "Darkstone Periapt";

    public CBR_StoneCalendar() {
        super(new StoneCalendar());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 7 + this.DESCRIPTIONS[1] + 52 + this.DESCRIPTIONS[2];
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        ++this.counter;
        if (this.counter == 7) {
            this.beginLongPulse();
        }
    }

    public void onPlayerEndTurn() {
        if (this.counter == 7) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, 52, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            this.stopPulse();
            this.grayscale = true;
        }

    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_StoneCalendar();
    }
}
