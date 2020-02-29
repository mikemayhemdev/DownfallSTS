package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import com.megacrit.cardcrawl.relics.GremlinMask;

public class CBR_MaskGremlin extends AbstractCharbossRelic {
    public static final String ID = "CBRMaskOfGremlin";
    public CBR_MaskGremlin() {
        super(new GremlinMask());
        this.tier = RelicTier.SPECIAL;
    }


    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new WeakPower(this.owner, 1, false), 1));
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskGremlin();
    }
}
