package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Kunai;

public class CBR_Kunai extends AbstractCharbossRelic {
    public static final String ID = "Kunai";

    public CBR_Kunai() {
        super(new Kunai());
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1] + 1 + this.DESCRIPTIONS[2];
    }

    @Override
    public void onPlayerEndTurn() {
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
