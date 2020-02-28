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
import com.megacrit.cardcrawl.relics.FusionHammer;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class CBR_FusionHammer extends AbstractCharbossRelic {
    public static final String ID = "FusionHammer";
    private int numCards;

    public CBR_FusionHammer() {
        super(new FusionHammer());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0] + this.numCards  + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        for (int i = actIndex; i < 3; i++) {
            if (this.owner.chosenArchetype.cardUpgradesPerAct[i] > 0) {
                this.owner.chosenArchetype.cardUpgradesPerAct[i] -= 1;
            this.numCards++;
            }

        }
        this.description = getUpdatedDescription();
        this.refreshDescription();
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
    public AbstractRelic makeCopy() {
        return new CBR_FusionHammer();
    }
}
