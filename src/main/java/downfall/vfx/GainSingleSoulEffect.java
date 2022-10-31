//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;
import downfall.downfallMod;

import java.util.Iterator;

public class GainSingleSoulEffect extends AbstractGameEffect {
    private static final float GRAVITY;
    private static final float START_VY;
    private static final float START_VY_JITTER;
    private static final float START_VX;
    private static final float START_VX_JITTER;
    private static final float TARGET_JITTER;
    private float rotationSpeed;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float targetX;
    private float targetY;
    private Texture img;
    private float alpha;
    private float suctionTimer;
    private float staggerTimer;
    private boolean showGainEffect;
    private AbstractCreature owner;

    public GainSingleSoulEffect(AbstractCreature owner, float x, float y, float targetX, float targetY, boolean showGainEffect) {
        this.alpha = 0.0F;
        this.suctionTimer = 1F;

        this.img = downfallMod.soulsImage;

        this.x = x - (float)this.img.getWidth() / 2.0F;
        this.y = y - (float)this.img.getHeight() / 2.0F;
        this.targetX = targetX + MathUtils.random(-TARGET_JITTER, TARGET_JITTER);
        this.targetY = targetY + MathUtils.random(-TARGET_JITTER, TARGET_JITTER * 2.0F);
        this.showGainEffect = showGainEffect;
        this.owner = owner;
        this.staggerTimer = MathUtils.random(0.0F, 1F);
        this.vX = MathUtils.random(START_VX - 50.0F * Settings.scale, START_VX_JITTER);
        this.rotationSpeed = MathUtils.random(500.0F, 2000.0F);
        if (MathUtils.randomBoolean()) {
            this.vX = -this.vX;
            this.rotationSpeed = -this.rotationSpeed;
        }

        this.vY = MathUtils.random(START_VY, START_VY_JITTER);
        this.scale = Settings.scale * 0.75F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public GainSingleSoulEffect(float x, float y) {
        this(AbstractDungeon.player, x, y, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true);
    }

    public void update() {
        if (this.staggerTimer > 0.0F) {
            this.staggerTimer -= Gdx.graphics.getDeltaTime();
        } else {
            if (this.alpha != 1.0F) {
                this.alpha += Gdx.graphics.getDeltaTime() * 2.0F;
                if (this.alpha > 1.0F) {
                    this.alpha = 1.0F;
                }

                this.color.a = this.alpha;
            }

            this.rotation += Gdx.graphics.getDeltaTime() * this.rotationSpeed;
            this.x += Gdx.graphics.getDeltaTime() * this.vX;
            this.y += Gdx.graphics.getDeltaTime() * this.vY;
            this.vY -= Gdx.graphics.getDeltaTime() * GRAVITY;
            if (this.suctionTimer > 0.0F) {
                this.suctionTimer -= Gdx.graphics.getDeltaTime();
            } else {
                this.vY = MathUtils.lerp(this.vY, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
                this.vX = MathUtils.lerp(this.vX, 0.0F, Gdx.graphics.getDeltaTime() * 5.0F);
                this.x = MathUtils.lerp(this.x, this.targetX, Gdx.graphics.getDeltaTime() * 4.0F);
                this.y = MathUtils.lerp(this.y, this.targetY, Gdx.graphics.getDeltaTime() * 4.0F);
                if (Math.abs(this.x - this.targetX) < 20.0F) {
                    this.isDone = true;
                   // if (MathUtils.randomBoolean()) {
                   //     CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
                    //}

                    if (!this.owner.isPlayer) {
                        this.owner.gainGold(1);
                    }

                    AbstractDungeon.effectsQueue.add(new ShineLinesEffect(this.x, this.y));
                    boolean textEffectFound = false;
                    Iterator var2 = AbstractDungeon.effectList.iterator();

                    AbstractGameEffect e;
                    while(var2.hasNext()) {
                        e = (AbstractGameEffect)var2.next();
                        if (e instanceof GainGoldTextEffect && ((GainGoldTextEffect)e).ping(1)) {
                            textEffectFound = true;
                            break;
                        }
                    }

                    if (!textEffectFound) {
                        var2 = AbstractDungeon.effectsQueue.iterator();

                        while(var2.hasNext()) {
                            e = (AbstractGameEffect)var2.next();
                            if (e instanceof GainGoldTextEffect && ((GainGoldTextEffect)e).ping(1)) {
                                textEffectFound = true;
                            }
                        }
                    }

                    if (!textEffectFound && this.showGainEffect) {
                        AbstractDungeon.effectsQueue.add(new GainGoldTextEffect(1));
                    }
                }
            }

        }
    }

    public void render(SpriteBatch sb) {
        if (this.staggerTimer <= 0.0F) {
            sb.setColor(this.color);
            sb.draw(this.img, this.x, this.y, (float)this.img.getWidth() / 2.0F, (float)this.img.getHeight() / 2.0F, this.img.getWidth(), this.img.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        }
    }

    public void dispose() {
    }

    static {
        GRAVITY = 500.0F * Settings.scale;
        START_VY = 800.0F * Settings.scale;
        START_VY_JITTER = 400.0F * Settings.scale;
        START_VX = 200.0F * Settings.scale;
        START_VX_JITTER = 300.0F * Settings.scale;
        TARGET_JITTER = 50.0F * Settings.scale;
    }
}
