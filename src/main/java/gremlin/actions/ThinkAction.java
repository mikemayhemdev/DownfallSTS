package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class ThinkAction
        extends AbstractGameAction
{
    private String msg;
    private boolean used = false;
    private float bubbleDuration;

    public ThinkAction(String text, float duration, float bubbleDuration)
    {
        setValues(source, AbstractDungeon.player);
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_MED;
        } else {
            this.duration = duration;
        }
        this.msg = text;
        this.actionType = AbstractGameAction.ActionType.TEXT;
        this.bubbleDuration = bubbleDuration;
    }

    public void update()
    {
        if (!this.used)
        {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, this.bubbleDuration, this.msg, this.source.isPlayer));
            this.used = true;
        }
        tickDuration();
    }
}

