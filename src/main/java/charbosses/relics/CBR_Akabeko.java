package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.cardpowers.EnemyVigorPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Akabeko;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_Akabeko extends AbstractCharbossRelic {
    public static final String ID = "Akabeko";

    public CBR_Akabeko() {
        super(new Akabeko());
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 8 + this.DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new EnemyVigorPower(this.owner, 8), 8));
    }
    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Akabeko();
    }
}
