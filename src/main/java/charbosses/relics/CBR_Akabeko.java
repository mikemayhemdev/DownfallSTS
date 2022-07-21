package charbosses.relics;

import charbosses.powers.general.EnemyVigorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;

public class CBR_Akabeko extends AbstractCharbossRelic {
    public static final String ID = "Akabeko";

    public CBR_Akabeko() {
        super(new Akabeko());
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 8 + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new EnemyVigorPower(this.owner, 8), 8));
    }
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Akabeko();
    }
}
