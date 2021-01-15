package champ.actions;

import basemod.ReflectionHacks;
import champ.patches.SuplexPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnimateSuplexAction extends AbstractGameAction
{
    private boolean called = false;
    private boolean flipped = false;
    private AbstractMonster mo;

    public AnimateSuplexAction(AbstractMonster m)
    {
        mo = m;
        startDuration = 1.0f;
        duration = startDuration;
        actionType = ActionType.WAIT;
    }

    @Override
    public void update()
    {
        if (!called) {
            called = true;
            mo.animX = 0;
            mo.animY = 0;
            //mo.vY = 500 * Settings.scale;
            ReflectionHacks.setPrivate(mo, AbstractCreature.class, "vY", 2000 * Settings.scale);
            //mo.animationTimer = 0.7f;
            ReflectionHacks.setPrivate(mo, AbstractCreature.class, "animationTimer", 0.7f);
            //mo.animation = AbstractCreature.CreatureAnimation.JUMP;
            ReflectionHacks.setPrivate(mo, AbstractCreature.class, "animation", SuplexPatch.REVERSE_GRAVITY);

        }
        if (!flipped && this.duration <= 0.5){
            mo.flipVertical = true;
            mo.animY += mo.hb.height;
            flipped = true;
        }
        tickDuration();
    }
}