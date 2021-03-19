package reskinContent.skinCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import guardian.GuardianMod;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import reskinContent.vfx.PortraitIntimidateEffect;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public class SSSSnecko extends AbstractSkinCharacter {
    public static TextureAtlas sneckoAtlas = null;
    public static Skeleton sneckoSkeleton;
    public static AnimationState sneckoState;
    public static AnimationStateData sneckoStateData;
    public static SkeletonData sneckoData;

    private static boolean confuseUsed = false;
    private static boolean confuseSFXUsed = false;
    private static boolean debuffSFXUsed = false;
    private static boolean waifuAppar = false;
    private static float sneckoTimer = 0.0f;
    private static float sneckoAfterImageTimer = 0.0f;

    private static Color halfWhite = Color.WHITE.cpy();
    private static float sneckoWaifuX = 0.0f;
    private static float sneckoWaifuY = 0.0f;

    public SSSSnecko() {
        this.original_IMG = ImageMaster.loadImage("sneckomodResources/images/charSelect/portrait.png");
        this.portrait_waifu = ImageMaster.loadImage(reskinContent.assetPath("img/SneckoMod/portrait_waifu.png"));
        this.portrait_waifu2 = ImageMaster.loadImage(reskinContent.assetPath("img/SneckoMod/portrait_waifu2.png"));

        this.name = CardCrawlGame.languagePack.getCharacterString("sneckomod:theSnecko").NAMES[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait");
    }

    @Override
    public void loadAnimation() {
        super.loadAnimation();
        sneckoAtlas = new TextureAtlas(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait_effect.atlas")));
        SkeletonJson sneckoJson = new SkeletonJson(sneckoAtlas);
        sneckoJson.setScale(Settings.scale / 1.0F);
        sneckoData = sneckoJson.readSkeletonData(Gdx.files.internal(reskinContent.assetPath("img/SneckoMod/animation/Snecko_portrait_effect.json")));


        sneckoSkeleton = new Skeleton(sneckoData);
        sneckoSkeleton.setColor(Color.WHITE);
        sneckoStateData = new AnimationStateData(sneckoData);
        sneckoState = new AnimationState(sneckoStateData);
        sneckoStateData.setDefaultMix(0.2F);

        sneckoState.setTimeScale(1.0f);
    }

    @Override
    public void setAnimation() {
        portraitState.addAnimation(0, "layout", true, 0.0f);
        sneckoState.addAnimation(0, "layout_effect", true, 0.0f);
    }

    @Override
    public void InitializeStaticPortraitVar() {
        confuseUsed = false;
        confuseSFXUsed = false;
        debuffSFXUsed = false;
        waifuAppar = false;
        sneckoTimer = 0.0f;
        sneckoAfterImageTimer = 0.0f;
    }

    @Override
    public void setPos() {
        portraitSkeleton.setPosition(1296.0f * Settings.scale, Settings.HEIGHT - 1276.0f * Settings.scale);
    }

    @Override
    public void skeletonRenderUpdate(SpriteBatch sb) {
        sneckoState.update(Gdx.graphics.getDeltaTime());
        sneckoState.apply(sneckoSkeleton);
        sneckoSkeleton.updateWorldTransform();

        sneckoSkeleton.setPosition(1296.0f * Settings.scale, Settings.HEIGHT - 1276.0f * Settings.scale);

        sneckoSkeleton.setColor(Color.WHITE.cpy());
        sneckoSkeleton.setFlip(false, false);
    }

    @Override
    public void skeletonRender(SpriteBatch sb) {
        if (portraitAnimationType == 2 && waifuAppar) {
            sneckoWaifuX = portraitSkeleton.findBone("Waifu1_root").getX();
            sneckoWaifuY = portraitSkeleton.findBone("Waifu1_root").getY();

            portraitSkeleton.setColor(halfWhite);


            portraitSkeleton.findBone("Waifu1_root").setPosition(
                    sneckoWaifuX + 100.0f * Settings.scale * (float) Math.cos(sneckoAfterImageTimer * Math.PI) - 100.0f * Settings.scale,
                    sneckoWaifuY + 50.0f * Settings.scale * (float) Math.sin(sneckoAfterImageTimer * Math.PI));
            portraitSkeleton.updateWorldTransform();
            sr.draw(CardCrawlGame.psb, portraitSkeleton);

            portraitSkeleton.setColor(Color.WHITE.cpy());
            portraitSkeleton.findBone("Waifu1_root").setPosition(sneckoWaifuX, sneckoWaifuY);
            portraitSkeleton.updateWorldTransform();
            sr.draw(CardCrawlGame.psb, portraitSkeleton);

            portraitSkeleton.setColor(halfWhite);
            portraitSkeleton.findBone("Waifu1_root").setPosition(
                    sneckoWaifuX + 100.0f * Settings.scale * (float) Math.cos((sneckoAfterImageTimer + 1.0f) * Math.PI) + 100.0f * Settings.scale,
                    sneckoWaifuY + 50.0f * Settings.scale * (float) Math.sin((sneckoAfterImageTimer + 1.0f) * Math.PI));
            portraitSkeleton.updateWorldTransform();
            sr.draw(CardCrawlGame.psb, portraitSkeleton);

        } else {
            sr.draw(CardCrawlGame.psb, portraitSkeleton);
        }

        sr.draw(CardCrawlGame.psb, sneckoSkeleton);


        CardCrawlGame.psb.end();
        sb.begin();
    }

    @Override
    public void update() {
        if (reskinCount == 1 && portraitAnimationType != 0) {
            sneckoTimer += Gdx.graphics.getDeltaTime();
            sneckoAfterImageTimer += Gdx.graphics.getDeltaTime();

            if (!confuseUsed && sneckoTimer > 1.0f) {

                CardCrawlGame.sound.play("MONSTER_SNECKO_GLARE");
                CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitIntimidateEffect(
                        portraitSkeleton.getX() + portraitSkeleton.findBone("Snecko_eyeBall_R").getWorldX(),
                        portraitSkeleton.getY() + portraitSkeleton.findBone("Snecko_eyeBall_R").getWorldY()
                ));
                CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitIntimidateEffect(
                        portraitSkeleton.getX() + portraitSkeleton.findBone("Snecko_eyeBall_L").getWorldX(),
                        portraitSkeleton.getY() + portraitSkeleton.findBone("Snecko_eyeBall_L").getWorldY()
                ));

                confuseUsed = true;
            }

            if (!confuseSFXUsed && sneckoTimer > 2.5f) {
                CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
                confuseSFXUsed = true;
            }

            if (!debuffSFXUsed && sneckoTimer > 7.5f) {
                int roll = MathUtils.random(0, 2);
                if (roll == 0) {
                    CardCrawlGame.sound.play("DEBUFF_1");
                } else if (roll == 1) {
                    CardCrawlGame.sound.play("DEBUFF_2");
                } else {
                    CardCrawlGame.sound.play("DEBUFF_3");
                }

                CardCrawlGame.sound.play("BLOCK_BREAK");
                debuffSFXUsed = true;
            }


            if (sneckoTimer > 3.1f) waifuAppar = true;
            if (sneckoTimer > 6.95f) waifuAppar = false;

            if (sneckoAfterImageTimer > 2.0f)
                sneckoAfterImageTimer = 0.0f;
            halfWhite.a = 0.2f + 0.3f * sneckoAfterImageTimer;

            if (sneckoTimer > 8.0f) {
                sneckoTimer = sneckoTimer % 1;
                confuseUsed = false;
                confuseSFXUsed = false;
                debuffSFXUsed = false;

            }
        }
    }
}


