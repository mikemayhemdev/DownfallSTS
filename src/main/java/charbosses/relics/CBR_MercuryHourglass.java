package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MercuryHourglass;

public class CBR_MercuryHourglass extends AbstractCharbossRelic {

    public CBR_MercuryHourglass() {
        super(new MercuryHourglass());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractCharBoss.boss, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MercuryHourglass();
    }
}
