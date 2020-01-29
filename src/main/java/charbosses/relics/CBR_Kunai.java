package charbosses.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Kunai;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

public class CBR_Kunai extends AbstractCharbossRelic
{
    
    public CBR_Kunai() {
        super(new Kunai());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1] + 1 + this.DESCRIPTIONS[2];
    }
    
    @Override
    public void atTurnStart() {
        this.counter = 0;
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractBossCard && card.type == AbstractCard.CardType.ATTACK) {
            ++this.counter;
            if (this.counter % 3 == 0) {
                this.counter = 0;
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
                this.addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new DexterityPower(AbstractCharBoss.boss, 1), 1));
            }
        }
    }
    
    @Override
    public void onVictory() {
        this.counter = -1;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Kunai();
    }
}
