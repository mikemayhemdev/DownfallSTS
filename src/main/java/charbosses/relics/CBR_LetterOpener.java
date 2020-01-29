package charbosses.relics;

import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LetterOpener;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;

public class CBR_LetterOpener extends AbstractCharbossRelic
{
    
    public CBR_LetterOpener() {
        super(new LetterOpener());
        this.counter = 0;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 5 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atTurnStart() {
        this.counter = 0;
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractBossCard && card.type == AbstractCard.CardType.SKILL) {
            ++this.counter;
            if (this.counter % 3 == 0) {
                this.flash();
                this.counter = 0;
                this.addToBot(new RelicAboveCreatureAction(this.owner, this));
                this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, 5, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
        }
    }
    
    @Override
    public void onVictory() {
        this.counter = -1;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_LetterOpener();
    }
}
