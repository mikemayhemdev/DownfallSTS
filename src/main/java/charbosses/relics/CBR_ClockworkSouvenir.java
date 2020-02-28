package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ClockworkSouvenir;

public class CBR_ClockworkSouvenir extends AbstractCharbossRelic {
    public static final String ID = "ClockworkSouvenir";

    public CBR_ClockworkSouvenir() {
        super(new ClockworkSouvenir(), RelicTier.UNCOMMON);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new ArtifactPower(this.owner, 1), 1));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ClockworkSouvenir();
    }
}
