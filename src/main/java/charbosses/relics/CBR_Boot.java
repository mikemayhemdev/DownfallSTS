package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Boot;

public class CBR_Boot extends AbstractCharbossRelic {
    public static final String ID = "Boot";

    public CBR_Boot() {
        super(new Boot());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 4 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Boot();
    }


    @Override
    public int onAttackToChangeDamage(final DamageInfo info, final int damageAmount) {
        //downfallMod.logger.info("Boot tried to trigger");

        //downfallMod.logger.info(info.owner);
        //downfallMod.logger.info(info.type);
        //downfallMod.logger.info(damageAmount);

        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && damageAmount < 5) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            //downfallMod.logger.info("Boot trigger success");
            return 5;
        }
        return damageAmount;
    }
}
