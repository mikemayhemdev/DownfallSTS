package charbosses.relics.EventRelics;

import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CultistMask;
import com.megacrit.cardcrawl.relics.FaceOfCleric;

public class CBR_MaskCultist extends AbstractCharbossRelic {
    public static final String ID = "CBRCultistMask";
    public CBR_MaskCultist() {
        super(new CultistMask());
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new SFXAction("VO_CULTIST_1A"));
        this.addToBot(new TalkAction(false, this.DESCRIPTIONS[1], 1.0F, 2.0F));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskCultist();
    }
}
