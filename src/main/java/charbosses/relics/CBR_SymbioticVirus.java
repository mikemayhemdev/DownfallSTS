package charbosses.relics;

import charbosses.orbs.EnemyDark;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.HandDrill;
import com.megacrit.cardcrawl.relics.SymbioticVirus;

public class CBR_SymbioticVirus extends AbstractCharbossRelic {
    public static final String ID = "SymbioticVirus";

    public CBR_SymbioticVirus() {
        super(new SymbioticVirus());
        this.tier = RelicTier.RARE;
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        this.owner.channelOrb(new EnemyDark());
    }

    public AbstractRelic makeCopy() {
        return new CBR_SymbioticVirus();
    }
}
