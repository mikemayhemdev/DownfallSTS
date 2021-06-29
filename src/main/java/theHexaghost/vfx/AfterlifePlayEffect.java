package theHexaghost.vfx;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;

public class AfterlifePlayEffect extends AbstractGameEffect {
    private AbstractCard card;
    private float deltaX;

    {
        duration = startingDuration = 1.5f;
    }

    public AfterlifePlayEffect(AbstractCard card) {
        this.card = card;
        this.color = ReflectionHacks.getPrivate(this.card, AbstractCard.class, "renderColor");
    }


    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            this.card.unfadeOut();
            AbstractDungeon.player.limbo.addToTop(this.card);
            this.card.current_x = this.card.target_x = (Settings.WIDTH / 2.0F);
            this.card.current_y = this.card.target_y = (Settings.HEIGHT / 2.0F);
            this.deltaX = MathUtils.random(Settings.scale * -100, Settings.scale * 100);
            this.card.angle = this.card.targetAngle = 0F;
            this.color.a = 0.0f;
            for (int i = 0; i < 50; ++i) {
                AbstractDungeon.effectsQueue.add(new SpookyEmberEffect(this.card.current_x, this.card.current_y));
            }
        }
        if (this.duration > 1.25f) {
            this.card.transparency = 1f - 4f * (this.duration - 1.25f);
        } else if (this.duration < 1f) {
            this.card.transparency = this.duration;
            float t = 1f - duration;
            this.card.current_x = this.card.target_x = Interpolation.pow2Out.apply(
                    Settings.WIDTH / 2.0f,
                    Settings.WIDTH / 2.0f + deltaX,
                    t
                    );
            this.card.current_y = this.card.target_y = Interpolation.pow2In.apply(
                    Settings.HEIGHT / 2.0F,
                    Settings.HEIGHT / 2.0F + 400 * Settings.scale,
                    t);

        }
        this.color.g = Interpolation.smooth.apply(.3f, 1f, this.duration / this.startingDuration);

        for (int i=0; i<10; i++) {
            float t = 1f - duration - 0.02f * i;
            if (t > 0.25f) {

                CardTrailEffect effect = Soul.trailEffectPool.obtain();
                effect.init(Interpolation.pow2Out.apply(
                        Settings.WIDTH / 2.0f,
                        Settings.WIDTH / 2.0f + deltaX,
                        t), Interpolation.pow2In.apply(
                        Settings.HEIGHT / 2.0F,
                        Settings.HEIGHT / 2.0F + 400 * Settings.scale,
                        t));
                AbstractDungeon.topLevelEffects.add(effect);
            }
        }
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
            AbstractDungeon.player.limbo.removeCard(this.card);
            for (int i = 0; i < 30; ++i) {
                AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(this.card.current_x, this.card.current_y));
            }
        }
    }

    @Override
    public void render(SpriteBatch paramSpriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
