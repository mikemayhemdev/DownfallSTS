 package reskinContent.skinCharacter.skins.Champ;

 import champ.ChampChar;
 import com.badlogic.gdx.graphics.Texture;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.reskinContent;
 import reskinContent.skinCharacter.AbstractSkin;
 import reskinContent.vfx.ReskinUnlockedTextEffect;
 import sneckomod.TheSnecko;

 public  class ChampSister extends AbstractSkin {


    public ChampSister() {
        this.portraitStatic_IMG =  ImageMaster.loadImage(reskinContent.assetPath("img/ChampMod/Chan-P/portrait_waifu.png"));
        this.portraitAnimation_IMG =  ImageMaster.loadImage(reskinContent.assetPath("img/ChampMod/Chan-P/portrait_waifu2.png"));

        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinChamp").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/ChampMod/Chan-P/animation/GuardianChan_portrait");
    }


     @Override
     public void InitializeStaticPortraitVar() {

     }

 }


