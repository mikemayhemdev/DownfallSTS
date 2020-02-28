package charbosses.relics;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Lantern;
import com.megacrit.cardcrawl.relics.LetterOpener;

public class CBR_Lantern extends AbstractCharbossRelic {
    public static final String ID = "Lantern";


    
    public CBR_Lantern() {
        super(new Lantern());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1] + LocalizedStrings.PERIOD;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new EnemyGainEnergyAction(1));
        this.addToBot(new RelicAboveCreatureAction(this.owner, this));

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Lantern();
    }
}
