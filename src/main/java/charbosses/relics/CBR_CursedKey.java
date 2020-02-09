package charbosses.relics;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import charbosses.bosses.AbstractCharBoss;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.core.*;

import java.util.ArrayList;

public class CBR_CursedKey extends AbstractCharbossRelic
{
    public static final String ID = "Cursed Key";
    
    public CBR_CursedKey() {
        super("Cursed Key", "cursedKey.png", RelicTier.BOSS, LandingSound.CLINK);
    }
    
    @Override
    public String getUpdatedDescription() {
        if (AbstractCharBoss.boss != null) {
            return this.setDescription(AbstractCharBoss.boss.chosenClass);
        }
        return this.setDescription(null);
    }
    
    @Override
    public void justEnteredRoom(final AbstractRoom room) {
        if (room instanceof TreasureRoom) {
            this.flash();
            this.pulse = true;
        }
        else {
            this.pulse = false;
        }
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

    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        AbstractCharBoss.boss.chosenArchetype.addRandomCurse(AbstractCharBoss.boss, "Cursed Key");
        if (AbstractDungeon.actNum > 2) {
            AbstractCharBoss.boss.chosenArchetype.addRandomCurse(AbstractCharBoss.boss, "Cursed Key");
        }

    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CursedKey();
    }
}
