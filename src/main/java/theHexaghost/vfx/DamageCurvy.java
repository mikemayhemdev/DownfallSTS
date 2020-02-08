package theHexaghost.vfx;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;

public class DamageCurvy extends DamageImpactCurvyEffect {
    public DamageCurvy(float x, float y, Color color) {
        this(x, y, color, MathUtils.random(0.8F, 1.1F), MathUtils.random(400.0F, 900.0F) * Settings.scale, MathUtils.random(5.0F, 30.0F), MathUtils.random(-20.0F, 20.0F));
    }

    public DamageCurvy(float x, float y, Color color, float duration, float speed, float waveIntensity, float waveSpeed) {
        super(x, y, color, false);

        this.duration = duration;
        this.startingDuration = this.duration;

        ReflectionHacks.setPrivate(this, DamageImpactCurvyEffect.class, "speed", speed);
        ReflectionHacks.setPrivate(this, DamageImpactCurvyEffect.class, "speedStart", speed);

        ReflectionHacks.setPrivate(this, DamageImpactCurvyEffect.class, "waveIntensity", waveIntensity);
        ReflectionHacks.setPrivate(this, DamageImpactCurvyEffect.class, "waveSpeed", waveSpeed);
    }
}