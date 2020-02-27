package charbosses.relics;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Lantern;
import com.megacrit.cardcrawl.relics.LetterOpener;

public class CBR_Lantern extends AbstractCharbossRelic {

    private boolean firstTurn = true;
    
    public CBR_Lantern() {
        super(new Lantern());
    }

    public void atPreBattle() {
        this.firstTurn = true;
    }

    public void atTurnStart() {
        if (this.firstTurn) {
            this.flash();
            this.addToTop(new EnemyGainEnergyAction(1));
            this.addToTop(new RelicAboveCreatureAction(this.owner, this));
            this.firstTurn = false;
        }

    }
    

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Lantern();
    }
}
