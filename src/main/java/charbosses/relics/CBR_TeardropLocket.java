package charbosses.relics;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.TeardropLocket;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CBR_TeardropLocket extends AbstractCharbossRelic {
    public static final String ID = "TeardropLocket";

    public CBR_TeardropLocket() {
        super(new TeardropLocket());
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new EnemyChangeStanceAction("Calm"));
        this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
    }

    public AbstractRelic makeCopy() {
        return new CBR_TeardropLocket();
    }
}
