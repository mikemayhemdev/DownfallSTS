package collector.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class ColoredVerticalAttackEffect extends AbstractGameEffect {
    private final float x;
    private final float y;
    private final Color slamColor;
    private final boolean isVertical;

    public ColoredVerticalAttackEffect(float x, float y, boolean isVertical, Color slamColor) {
        this.x = x;
        this.y = y;
        this.slamColor = slamColor;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.isVertical = isVertical;
    }

    public void update() {
        CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", 0.8F);
        if (this.isVertical) {
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 30.0F * Settings.scale, 0.0F, -500.0F, 180.0F, 5.0F, slamColor, slamColor));
        } else {
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 30.0F * Settings.scale, -500.0F, -500.0F, 135.0F, 4.0F, slamColor, slamColor));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
