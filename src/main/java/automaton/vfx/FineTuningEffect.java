package automaton.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.UpgradeHammerImprintEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;

public class FineTuningEffect extends AbstractGameEffect {
    private boolean clang1 = false;
    private boolean clang2 = false;
    private AbstractCard owningCard;

    public FineTuningEffect(AbstractCard c) {
        this.duration = 0.8F;
        owningCard = c;
    }

    public void update() {
        if ((this.duration < 0.6F) && (!this.clang1)) {
            CardCrawlGame.sound.playA("CARD_UPGRADE", 0.1F);
            this.clang1 = true;
            clank(owningCard.current_x - (80.0F * owningCard.targetDrawScale) * Settings.scale, owningCard.target_y + (0.0F* owningCard.targetDrawScale) * Settings.scale);
            //CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
        }

        if ((this.duration < 0.2F) && (!this.clang2)) {
            this.clang2 = true;
            clank(owningCard.current_x + (90.0F * owningCard.targetDrawScale) * Settings.scale, owningCard.target_y - (110.0F* owningCard.targetDrawScale) * Settings.scale);
            //CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
        }

        this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            clank(owningCard.current_x + (30.0F * owningCard.targetDrawScale)* Settings.scale, owningCard.target_y + (120.0F* owningCard.targetDrawScale) * Settings.scale);
            this.isDone = true;
            //CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
        }
    }

    private void clank(float x, float y) {
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.topLevelEffectsQueue.add(new ScaledHammerImprintEffect(x, y, owningCard.targetDrawScale));
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.topLevelEffectsQueue.add(new TextAboveCreatureEffect(x,y - 200F * owningCard.targetDrawScale,"+1", Color.LIME));
        if (Settings.DISABLE_EFFECTS) {
            return;
        }

        for (int i = 0; i < 10; i++) {
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x +

                    MathUtils.random(-10.0F, 10.0F) * Settings.scale * scale, y +
                    MathUtils.random(-10.0F, 10.0F) * Settings.scale * scale));
        }
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
    }

    public void dispose() {
    }
}

