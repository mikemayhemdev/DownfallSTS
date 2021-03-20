 package reskinContent.skinCharacter;

 import com.badlogic.gdx.graphics.Texture;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.reskinContent;

 public  class ChampSister extends AbstractSkinCharacter {


    public ChampSister() {
        super();
        this.original_IMG = ImageMaster.loadImage("champResources/images/charSelect/charBG.png");
        this.portrait_waifu =  ImageMaster.loadImage(reskinContent.assetPath("img/ChampMod/portrait_waifu.png"));
        this.portrait_waifu2 =  ImageMaster.loadImage(reskinContent.assetPath("img/ChampMod/portrait_waifu2.png"));

        this.ID = CardCrawlGame.languagePack.getCharacterString("champ:theChamp").NAMES[0];
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinChamp").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/ChampMod/animation/GuardianChan_portrait");
    }

     @Override
     public void InitializeStaticPortraitVar() {

     }

 }


