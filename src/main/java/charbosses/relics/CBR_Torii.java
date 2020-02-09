package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Torii;

public class CBR_Torii extends AbstractCharbossRelic {

    public CBR_Torii() {
        super(new Torii());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 5 + this.DESCRIPTIONS[1];
    }

    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 1 && damageAmount <= 5) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
            return 1;
        }
        return damageAmount;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Torii();
    }
}
