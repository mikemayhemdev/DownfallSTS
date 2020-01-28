package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Boot;

public class CBR_Boot extends AbstractCharbossRelic {
	public CBR_Boot() {
		super(new Boot());
	}

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 4 + this.DESCRIPTIONS[1];
    }
	@Override
	public AbstractRelic makeCopy() {
		// TODO Auto-generated method stub
		return new CBR_Boot();
	}
	
	@Override
    public int onAttackToChangeDamage(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && damageAmount < 5) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            return 5;
        }
        return damageAmount;
    }
}
