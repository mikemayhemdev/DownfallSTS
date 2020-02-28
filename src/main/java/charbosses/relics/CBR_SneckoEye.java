package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.relics.SneckoEye;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class CBR_SneckoEye extends AbstractCharbossRelic {
    public static final String ID = "SneckoEye";

    public CBR_SneckoEye() {
        super(new SneckoEye());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.owner.masterHandSize += 1;
    }

    @Override
    public void onUnequip() {
        this.owner.masterHandSize -= 1;
    }


    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ConfusionPower(this.owner)));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_SneckoEye();
    }
}
