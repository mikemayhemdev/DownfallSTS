package sneckomod.cards;

import basemod.ReflectionHacks;
import collector.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import downfall.util.TextureLoader;
import hermit.patches.EnumPatch;
import sneckomod.SneckoMod;

public class Blunderbus extends AbstractSneckoCard {

    public final static String ID = SneckoMod.makeID("Blunderbus");

    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int BASE_HITS = 1;

    public Blunderbus() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "Blunderbus.png");

        // Initialize multiDamage for all enemies
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.actB(() -> {
            int additionalHits = 0;
            if (this.costForTurn >= 3)
                additionalHits++;
            for (AbstractCard card : AbstractDungeon.player.hand.group)
                if (card.costForTurn >= 3 && card != this)
                    additionalHits++;
    
            AbstractMonster mo = Wiz.getEnemies().get(0);
            if (mo == null) return;
            for (int i = 0; i < BASE_HITS + additionalHits; i++) {
                if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(cardID, false)) {
                    Wiz.forAllMonstersLiving(mo2 -> Wiz.vfxTop(new CreatureFlyEffect(mo2)));
                    att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                    Wiz.vfxTop(new VerticalImpactEffect(mo.hb.cX + mo.hb.width / 4.0F, mo.hb.cY - mo.hb.height / 4.0F), BlunderbusEffect.STALL_DURATION);
                    Wiz.vfxTop(new BlunderbusEffect(mo), BlunderbusEffect.DURATION - 0.3f);
                    att(new SFXAction(SneckoMod.makeID("BUS")));
                } else {
                    for (int j = 0; j < 3; j++) {
                        Wiz.vfxTop(new FlashAtkImgEffect(mo.hb.cX + MathUtils.random(-100f, 100f) * Settings.scale, mo.hb.cY + MathUtils.random(-100f, 100f) * Settings.scale, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true), 0.02f);
                        att(new SFXAction("BLUNT_FAST", 0.02f));
                    }
                    att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                    Wiz.vfxTop(new BlunderbussEffect(mo), BlunderbussEffect.DURATION / 2f - 0.05f);
                }
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    private static class BlunderbussEffect extends AbstractGameEffect {
        private static float DURATION = 0.8f;
        private static float JUMP_HEIGHT = 250f * Settings.scale;

        private boolean fired;
        private AbstractMonster target;

        public BlunderbussEffect(AbstractMonster target) {
            this.target = target;
            duration = DURATION;
        }

        public void update() {
            duration -= Gdx.graphics.getDeltaTime();
            float progress = 1f - duration / DURATION;
            if (duration <= 0f) {
                AbstractDungeon.player.animY = 0f;
                isDone = true;
                return;
            }
            AbstractDungeon.player.animY = (float)Math.sin(progress * Math.PI) * JUMP_HEIGHT;
            if (progress >= 0.5f && !fired) {
                fired = true;
                CardCrawlGame.sound.play(SneckoMod.makeID("SHOTGUN"));
                float x = AbstractDungeon.player.hb.x + AbstractDungeon.player.hb.width;
                float y = AbstractDungeon.player.animY + AbstractDungeon.player.drawY;
                if (target != null)
                    for (int i = 0; i < 10; i++)
                        AbstractDungeon.effectsQueue.add(new ShotgunPelletEffect(x, y, target.hb.cY));
                for (int i = 0; i < 15; i++)
                    AbstractDungeon.effectsQueue.add(new ShotgunSmokeEffect(x, y));
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
        

        private static class ShotgunPelletEffect extends AbstractGameEffect {
            private static TextureAtlas.AtlasRegion IMG = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(SneckoMod.makeImagePath("vfx/shotgunpellet.png")), 0, 0, 64, 64);
            private static final float DURATION = 0.2f;

            private float x, y, startX, startY, endX, endY;

            public ShotgunPelletEffect(float startX, float startY, float endY) {
                duration = DURATION;
                color = Color.WHITE.cpy();
                this.startX = startX;
                this.startY = startY;
                x = startX;
                y = startY;
                this.endX = Settings.WIDTH + MathUtils.random(128f, 500f) * Settings.scale;
                this.endY = endY + MathUtils.random(-400f, 400f) * Settings.scale;
            }

            public void update() {
                duration -= Gdx.graphics.getDeltaTime();
                float progress = 1f - duration / DURATION;
                x = startX + (endX - startX) * progress;
                y = startY + (endY - startY) * progress;
                if (duration <= 0f)
                    isDone = true;
            }

            public void render(SpriteBatch sb) {
                sb.setColor(color);
                sb.draw(IMG, x - 32f, y - 32f, 64f * scale, 64f * scale);
            }

            public void dispose() {}
        }

        private static class ShotgunSmokeEffect extends AbstractGameEffect {
            private static final float DURATION = 0.45f;
            private static final float OPACITY = 0.9f;
            private static final float FINAL_SCALE = 0.3f;
            private float x, y;
            private float xVel = MathUtils.random(0f, 200f) * Settings.scale;
            private float yVel = MathUtils.random(-100f, 100f) * Settings.scale;
            private float rotationalVel = MathUtils.random(-50f, 50f);
            private float startScale;
            private boolean shook = false;
            private TextureAtlas.AtlasRegion img;

            public ShotgunSmokeEffect(float x, float y) {
                this.x = x;
                this.y = y;
                duration = DURATION;
                rotation = MathUtils.random(0f, 360f);
                scale *= MathUtils.random(0.75f, 1.25f);
                startScale = scale;
                color = new Color(0.95f, 0.95f, 0.95f, OPACITY);
                
                img = MathUtils.randomBoolean(0.5f) ? ImageMaster.EXHAUST_L : ImageMaster.EXHAUST_S;
            }

            public void update() {
                if (!shook) {
                    shook = true;
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); 
                }
                color.a = (duration / DURATION) * OPACITY;
                scale = startScale * (FINAL_SCALE + (duration / DURATION) * (1f - FINAL_SCALE));
                rotation += rotationalVel * Gdx.graphics.getDeltaTime();
                x += xVel * Gdx.graphics.getDeltaTime();
                y += yVel * Gdx.graphics.getDeltaTime();
                duration -= Gdx.graphics.getDeltaTime();
                if (duration < 0f)
                   isDone = true;
            }

            public void render(SpriteBatch sb) {
                sb.setColor(color);
                sb.draw(img, x, y, img.packedWidth / 2.0F, img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
            }

            public void dispose() {}
        }
    }

    private static class BlunderbusEffect extends AbstractGameEffect {
        private static TextureAtlas.AtlasRegion IMG = new TextureAtlas.AtlasRegion(TextureLoader.getTexture(SneckoMod.makeImagePath("vfx/bus.png")), 0, 0, 1000, 400);
        private static float DURATION = 0.6f,
            STALL_DURATION = 0.2f;

        private float x = -500 * scale, y = AbstractDungeon.floorY + 100f * scale, targetX, timeStalled;

        public BlunderbusEffect(AbstractMonster target) {
            targetX = target.hb.cX - 400f * scale;
            duration = DURATION;
        }

        public void update() {
            if (duration > 0f)
                duration = Math.max(duration - Gdx.graphics.getDeltaTime(), 0f);
            else if (duration == 0f) {
                timeStalled += Gdx.graphics.getDeltaTime();
                if (timeStalled > STALL_DURATION)
                    duration = -0.01f;
            } else
                duration -= Gdx.graphics.getDeltaTime();
            
            x = targetX + (-400f * scale - targetX) * (duration / DURATION);
            isDone = x > Settings.WIDTH + 600f * scale;
        }

        public void render(SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(IMG, x - 500f, y - 200f, 500, 200, 1000, 400, scale, scale, rotation);
        }

        public void dispose() {}
    }

    public static class CreatureFlyEffect extends AbstractGameEffect {
        private static final float ACCELERATION = -2000f;
        private static final float SMOKE_GAP = 0.05f;
        public AbstractCreature target;
        private Bone rootBone;
        private float initialRotation;
        private float y = 0f;
        private float vel = MathUtils.random(1200f, 1800f);
        private float wholeDuration = vel * 2f / -ACCELERATION;
        private float timer = 0f;
        private float smokeTimer = SMOKE_GAP;

        public CreatureFlyEffect(AbstractCreature target) {
            this.target = target;
            if (target != null) {
                Skeleton skeleton = ReflectionHacks.getPrivate(target, AbstractCreature.class, "skeleton");
                if (skeleton != null) {
                    rootBone = skeleton.getRootBone();
                }
            }
        }

        public void update() {
            if (target == null) {
                isDone = true;
                return;
            }
            for (AbstractGameEffect effect : AbstractDungeon.effectList) {
                if (effect == this)
                    break;
                else if (effect instanceof CreatureFlyEffect && ((CreatureFlyEffect)effect).target == target)
                    return;
            }
            if (timer == 0f && rootBone != null)
                initialRotation = rootBone.getRotation();
            while (smokeTimer >= SMOKE_GAP) {
                smokeTimer -= SMOKE_GAP;
                AbstractDungeon.effectsQueue.add(new RumbleSmokeEffect(target.hb.cX, target.animY + target.drawY));
            }
            y += vel * Gdx.graphics.getDeltaTime() * Settings.scale;
            vel += ACCELERATION * Gdx.graphics.getDeltaTime();
            timer += Gdx.graphics.getDeltaTime();
            smokeTimer += Gdx.graphics.getDeltaTime();
            if (y <= 0f) {
                target.animY = 0;
                if (rootBone != null)
                    rootBone.setRotation(initialRotation);
                isDone = true;
            } else {
                target.animY = y;
                if (rootBone != null)
                    rootBone.setRotation(initialRotation + Math.min(timer / wholeDuration, 1f) * 360f);
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}

        private static class RumbleSmokeEffect extends AbstractGameEffect {
            private static float DURATION = 0.6f;
            private static float OPACITY = 0.5f;
            private static float FINAL_SCALE = 0.3f;
            private float x, y;
            private float rotationalVel = MathUtils.random(-50f, 50f);
            private float startScale;
            private boolean shook = false;
            private TextureAtlas.AtlasRegion img;

            public RumbleSmokeEffect(float x, float y) {
                this.x = x;
                this.y = y;
                renderBehind = true;
                duration = DURATION;
                rotation = MathUtils.random(0f, 360f);
                scale *= MathUtils.random(0.5f, 1.5f);
                startScale = scale;
                color = new Color(0.7f, 0.7f, 0.7f, OPACITY);
                img = MathUtils.randomBoolean(0.5f) ? ImageMaster.EXHAUST_L : ImageMaster.EXHAUST_S;
            }

            public void update() {
                if (!shook) {
                    shook = true;
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); 
                }
                color.a = (duration / DURATION) * OPACITY;
                scale = startScale * (FINAL_SCALE + (duration / DURATION) * (1f - FINAL_SCALE));
                rotation += rotationalVel * Gdx.graphics.getDeltaTime();
                duration -= Gdx.graphics.getDeltaTime();
                if (duration < 0f)
                   isDone = true;
            }

            public void render(SpriteBatch sb) {
                sb.setColor(color);
                sb.draw(img, x, y, img.packedWidth / 2.0F, img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
            }

            public void dispose() {}
        }
    }
}
