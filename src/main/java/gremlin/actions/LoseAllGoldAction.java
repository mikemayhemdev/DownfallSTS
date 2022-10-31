package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class LoseAllGoldAction
        extends AbstractGameAction
{
    private static final float DURATION = 0.1F;

    public LoseAllGoldAction(AbstractCreature source)
    {
        this.actionType = ActionType.DEBUFF;
        this.duration = DURATION;
        this.source = source;
    }

    public void update()
    {
        if (this.duration == 0.1F)
        {
            int loseGold = AbstractDungeon.player.gold;
            AbstractPlayer p = AbstractDungeon.player;
            p.loseGold(loseGold);
            for (int i = 0; i < loseGold; i++) {
                AbstractDungeon.effectList.add(
                        new GainPennyEffect(p, p.hb.cX, p.hb.cY, source.hb.x, source.hb.y, false));
            }
        }
        tickDuration();
    }
}

