package timeEater;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import timeEater.actions.ResetClockAction;
import timeEater.powers.OnTickPower;
import timeEater.relics.OnTickRelic;
import timeEater.util.TextureLoader;

public class ClockHelper {

    private static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    private static Texture clockTex = TextureLoader.getTexture("timeResources/images/ui/clock.png");
    private static Texture handTex = TextureLoader.getTexture("timeResources/images/ui/hand.png");

    public static float CLOCK_DRAW_X = 500 * Settings.scale;
    public static float CLOCK_DRAW_Y = 500 * Settings.scale; //TODO: Adjust

    public static boolean active = false;

    public static int clock = 0;

    public static void tick() {
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
            atb(new ResetClockAction());
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
                    AbstractDungeon.actionManager.callEndTurnEarlySequence();
                    CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
                    AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
                    AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                }
            });
        }
    }

    public static void update() {
        if (active) {
            //TODO: On hover, do a hover thing
        }
    }

    public static float getHandAngle() {
        float x = 45 - clock * 30;
        if (x < 0) x += 360;
        return x;
    }

    public static void render(SpriteBatch sb) {
        if (active) {
            sb.setColor(Color.WHITE);
            sb.draw(clockTex, CLOCK_DRAW_X, CLOCK_DRAW_Y, 0, 0, clockTex.getWidth() * Settings.scale, clockTex.getHeight() * Settings.scale, 1, 1, 0, 0, 0, clockTex.getWidth(), clockTex.getHeight(), false, false);
            sb.draw(handTex, CLOCK_DRAW_X, CLOCK_DRAW_Y, 0, 0, clockTex.getWidth() * Settings.scale, clockTex.getHeight() * Settings.scale, 1, 1, getHandAngle(), 0, 0, clockTex.getWidth(), clockTex.getHeight(), false, false);
        }
    }
}
