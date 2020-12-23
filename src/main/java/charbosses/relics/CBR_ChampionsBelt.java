package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Boot;
import com.megacrit.cardcrawl.relics.ChampionsBelt;

public class CBR_ChampionsBelt extends AbstractCharbossRelic {
    public static final String ID = "Champion Belt";
    public static final int EFFECT = 1;

    public CBR_ChampionsBelt() {
        super(new ChampionsBelt());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    public void onTrigger() {
        this.addToBot(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractCharBoss.boss, new WeakPower(AbstractDungeon.player, 1, true), 1));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ChampionsBelt();
    }
}
