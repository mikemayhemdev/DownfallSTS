package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ClockworkSouvenir;

public class CBR_ClockworkSouvenir extends AbstractCharbossRelic {
    public static final String ID = "ClockworkSouvenir";

    public CBR_ClockworkSouvenir() {
        super(new ClockworkSouvenir(), RelicTier.COMMON);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new ArtifactPower(AbstractCharBoss.boss, 1), 1));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ClockworkSouvenir();
    }
}
