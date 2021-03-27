 package reskinContent.skinCharacter.skins.Automaton;

 import automaton.AutomatonChar;
 import com.badlogic.gdx.graphics.Texture;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.reskinContent;
 import reskinContent.skinCharacter.AbstractSkin;
 import reskinContent.vfx.ReskinUnlockedTextEffect;
 import sneckomod.TheSnecko;

 public  class Automaton extends AbstractSkin {


    public Automaton() {
        this.portraitStatic_IMG =  ImageMaster.loadImage(reskinContent.assetPath("img/BronzeMod/Automaton/portrait_waifu.png"));
        this.portraitAnimation_IMG =  ImageMaster.loadImage(reskinContent.assetPath("img/BronzeMod/Automaton/portrait_waifu2.png"));

        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinBronze").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/BronzeMod/Automaton/animation/GuardianChan_portrait");
    }


     @Override
     public void InitializeStaticPortraitVar() {

     }
 }


