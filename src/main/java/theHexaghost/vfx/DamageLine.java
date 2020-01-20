package theHexaghost.vfx;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;

public class DamageLine extends DamageImpactLineEffect {
    public DamageLine(float x, float y, Color color, double initialAngle) {
        this(x, y, color, initialAngle, MathUtils.random(20.0F * Settings.scale, 40.0F * Settings.scale), 0.5F);
    }
    public DamageLine(float x, float y, Color color, double initialAngle, float speed, float duration) {
        super(x, y);

        this.duration = duration;
        this.startingDuration = duration;

        Vector2 speedVector = new Vector2((float) Math.cos(initialAngle), (float) Math.sin(initialAngle));
        speedVector.nor();
        speedVector.angle();
        speedVector.x *= speed;
        speedVector.y *= speed;
        this.rotation = speedVector.angle();
        ReflectionHacks.setPrivate(this, DamageImpactLineEffect.class, "speed", speed);
        ReflectionHacks.setPrivate(this, DamageImpactLineEffect.class, "speedVector", speedVector);
        this.color = color;
    }
}