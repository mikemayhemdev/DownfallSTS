package timeEater;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import timeEater.actions.ResetClockAction;
import timeEater.powers.OnTickPower;
import timeEater.relics.OnTickRelic;

public class ClockHelper {

    private static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static boolean active = false;

    public static int clock = 1;

    public static void advance() {
        clock += 1;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnTickPower) {
                ((OnTickPower) p).onTick();
            }
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnTickRelic) {
                ((OnTickRelic) r).onTick();
            }
        }
        if (clock == 12) {
            AbstractDungeon.actionManager.callEndTurnEarlySequence();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            applyToSelf(new StrengthPower(AbstractDungeon.player, 2));
            atb(new ResetClockAction());
        }
    }

    public static void update() {
        //TODO: On hover, do a hover thing
    }

    public static void render() {
        //TODO: Actually draw the Clock.
        //Also probably a panel that shows ongoing Clock effects. if you're on 7, and you have a Lucky Sevens power, display "7 Effects: draw an additional card at the start of your turn", or w/eg
    }
}
