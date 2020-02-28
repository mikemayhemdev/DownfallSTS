package charbosses.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FossilizedHelix;
import com.megacrit.cardcrawl.relics.PenNib;

public class CBR_FossilizedHelix extends AbstractCharbossRelic {
    public static final String ID = "FossilizedHelix";

    public CBR_FossilizedHelix() {
        super(new FossilizedHelix());
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new BufferPower(this.owner, 1), 1));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_FossilizedHelix();
    }
}
