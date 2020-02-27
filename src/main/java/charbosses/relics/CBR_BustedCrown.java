package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BustedCrown;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class CBR_BustedCrown extends AbstractCharbossRelic {
    public static final String ID = "BustedCrown";

    public CBR_BustedCrown() {
        super(new BustedCrown());
    }

    @Override
    public String getUpdatedDescription() {
        if (AbstractCharBoss.boss != null) {
            return this.setDescription(AbstractCharBoss.boss.chosenClass);
        }
        return this.setDescription(null);
    }

    private String setDescription(final AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0];
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
        this.owner.damage(new DamageInfo(this.owner, MathUtils.floor(this.owner.maxHealth * 0.15F), DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractCharBoss.boss.energy;
        --energy.energyMaster;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        for (int i = 0; i <= 2; i++) {
            if (this.owner.chosenArchetype.synergyCardAcquisitionsPerAct[i] > 0) {
                this.owner.chosenArchetype.logger.info("Busted Crown is removing " + this.owner.chosenArchetype.synergyCardAcquisitionsPerAct[i] + " synergy card picks and replacing them with global picks in Act " + (i + 1));
                this.owner.chosenArchetype.globalCardAcquisitionsPerAct[i] += this.owner.chosenArchetype.synergyCardAcquisitionsPerAct[i];
                this.owner.chosenArchetype.synergyCardAcquisitionsPerAct[i] = 0;
            }
        }

    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.actNum <= 1;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BustedCrown();
    }
}
