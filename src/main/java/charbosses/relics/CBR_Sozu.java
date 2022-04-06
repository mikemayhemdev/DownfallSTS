package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FusionHammer;
import com.megacrit.cardcrawl.relics.Sozu;

public class CBR_Sozu extends AbstractCharbossRelic {
    public static final String ID = "Sozu";

    public CBR_Sozu() {
        super(new Sozu());
    }

    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        ++energy.energyMaster;

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Sozu();
    }
}
