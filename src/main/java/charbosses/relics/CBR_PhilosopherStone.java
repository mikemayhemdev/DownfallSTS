package charbosses.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PhilosopherStone;

import charbosses.bosses.AbstractCharBoss;

import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class CBR_PhilosopherStone extends AbstractCharbossRelic
{
    
    public CBR_PhilosopherStone() {
        super(new PhilosopherStone());
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, 1));
        AbstractDungeon.onModifyPower();
    }
    
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        ++energy.energyMaster;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        --energy.energyMaster;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PhilosopherStone();
    }
}
