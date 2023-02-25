package reskinContent.skinCharacter.skins.Automaton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import downfall.util.TextureLoader;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public class ThePerfect extends AbstractSkin {

    public ThePerfect() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinBronze").TEXT[2];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinBronze").EXTRA_TEXT[2];
        this.portraitAnimation_IMG = TextureLoader.getTexture(getAssetPath("portrait_beta_automaton_BG.png"));
        this.portraitAnimationType = 1;
        this.forcePortraitAnimationType = true;

        this.SHOULDER1 = getAssetPath("shoulder2.png");
        this.SHOULDER2 = getAssetPath("shoulder.png");
        this.CORPSE = getAssetPath("corpse.png");
        this.portraitAtlasPath = getAssetPath("animation/ThePerfect");
        this.atlasURL = portraitAtlasPath+".atlas";
        this.jsonURL = portraitAtlasPath+".json";
        this.renderscale = 1.0f;

        loadPortraitAnimation();
        this.portraitAtlasPath = null;
    }

    @Override
    public Texture updateBgImg() {
        reskinContent.saveSettings();
        return portraitAnimation_IMG;
    }

    @Override
    public void setAnimation() {
        portraitState.addAnimation(0, "idle", true, 0.0f);
    }

    @Override
    public void render(SpriteBatch sb) {
        portraitState.update(Gdx.graphics.getDeltaTime());
        portraitState.apply(portraitSkeleton);
        portraitSkeleton.updateWorldTransform();
        portraitSkeleton.setPosition(1400.0f * Settings.scale, Settings.HEIGHT - 1050.0f * Settings.scale);
        portraitSkeleton.setColor(Color.WHITE.cpy());
        portraitSkeleton.setFlipX(true);
        portraitSkeleton.getRootBone().setScale(1.6F);

        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, portraitSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/BronzeMod/" + this.getClass().getSimpleName() + "/" + filename);
    }
}