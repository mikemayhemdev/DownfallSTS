package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FusionHammer;
import com.megacrit.cardcrawl.relics.RunicDome;
import evilWithin.EvilWithinMod;

public class CBR_RunicDome extends AbstractCharbossRelic {
    public static final String ID = "RunicDome";

    public CBR_RunicDome() {
        super(new RunicDome());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0];

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
        return new CBR_RunicDome();
    }
}
