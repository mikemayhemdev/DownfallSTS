package charbosses.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Ectoplasm;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.core.*;

public class CBR_Ectoplasm extends AbstractCharbossRelic
{
    public static final String ID = "Ectoplasm";
    
    public CBR_Ectoplasm() {
        super(new Ectoplasm());
    }
    
    @Override
    public String getUpdatedDescription() {
        if (AbstractCharBoss.boss != null) {
            return this.setDescription(AbstractCharBoss.boss.chosenClass);
        }
        return this.setDescription(null);
    }
    
    private String setDescription(final AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    }
    
    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        ++energy.energyMaster;
        AbstractCharBoss.boss.relicBudget -= (AbstractDungeon.actNum - 1);
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        --energy.energyMaster;
    }
    
    @Override
    public boolean canSpawn() {
        return AbstractDungeon.actNum <= 1;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Ectoplasm();
    }
}
