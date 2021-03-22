package reskinContent.skinCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import reskinContent.reskinContent;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public abstract class AbstractSkinCharacter {
    public Texture original_IMG;
    public Texture portrait_waifu;
    public Texture portrait_waifu2;

    public TextureAtlas portraitAtlas = null;
    public Skeleton portraitSkeleton;
    public AnimationState portraitState;
    public AnimationStateData portraitStateData;
    public SkeletonData portraitData;

    public String portraitAtlasPath;

    public String ID = "";
    public String NAME = "";


    public boolean reskinUnlock = false;
    public int portraitAnimationType = 0;


    public int reskinCount = 0;

    public int reskinSize = 1;

    public AbstractSkinCharacter() {

    }

    public void loadPortraitAnimation() {
        loadAnimation();
        setAnimation();
        InitializeStaticPortraitVar();
    }


    public void loadAnimation(){
        portraitAtlas = new TextureAtlas(Gdx.files.internal(portraitAtlasPath + ".atlas"));
        SkeletonJson json = new SkeletonJson(portraitAtlas);
        json.setScale(Settings.scale / 1.0F);
        portraitData = json.readSkeletonData(Gdx.files.internal(portraitAtlasPath + ".json"));


        portraitSkeleton = new Skeleton(portraitData);
        portraitSkeleton.setColor(Color.WHITE);
        portraitStateData = new AnimationStateData(portraitData);
        portraitState = new AnimationState(portraitStateData);
        portraitStateData.setDefaultMix(0.2F);

        portraitState.setTimeScale(1.0f);
    }



    public void setAnimation(){
        portraitState.setAnimation(1, "fade_in", false);
        portraitState.addAnimation(0, "idle", true, 0.0f);
        InitializeStaticPortraitVar();
    }

    public void InitializeStaticPortraitVar() {

    }

    public Texture updateBgImg() {
        switch (reskinCount) {
            case 0:
                reskinContent.saveSettings();
                return original_IMG;
            case 1:
                reskinContent.saveSettings();
                if (portraitAnimationType == 0) {
                    return portrait_waifu;
                } else {
                    return portrait_waifu2;
                }
            default:
                return original_IMG;
        }
    }


    public void InitializeReskinCount() {
            if (this.reskinCount < 0)
                this.reskinCount = 0;
    }

    public void render(SpriteBatch sb){
        portraitState.update(Gdx.graphics.getDeltaTime());
        portraitState.apply(portraitSkeleton);
        portraitSkeleton.updateWorldTransform();

        setPos();
        skeletonRenderUpdate(sb);
        portraitSkeleton.setColor(Color.WHITE.cpy());
        portraitSkeleton.setFlip(false, false);

        sb.end();
        CardCrawlGame.psb.begin();
        skeletonRender(sb);

    }

    public void setPos(){
    }

    public void skeletonRenderUpdate(SpriteBatch sb){

    }

    public void skeletonRender(SpriteBatch sb){
        sr.draw(CardCrawlGame.psb, portraitSkeleton);

        CardCrawlGame.psb.end();
        sb.begin();
    }

    public void update(){}

    public void clearOrbs(){

    }

    public Boolean isOriginal(){
        return this.reskinCount <= 0;
    }

    public void checkUnlock(){}

}


