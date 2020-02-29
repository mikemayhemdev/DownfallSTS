package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BagOfMarbles;
import com.megacrit.cardcrawl.relics.RedMask;

public class CBR_RedMask extends AbstractCharbossRelic {
    public static final String ID = "RedMask";

    public CBR_RedMask() {
        super(new RedMask());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, this.owner, new WeakPower(AbstractDungeon.player, 1, true), 1, true));


    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_RedMask();
    }
    
}
