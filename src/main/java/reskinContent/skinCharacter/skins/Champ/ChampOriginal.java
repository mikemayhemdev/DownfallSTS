 package reskinContent.skinCharacter.skins.Champ;


 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.skinCharacter.AbstractSkin;

 public  class ChampOriginal extends AbstractSkin {

    public ChampOriginal() {
        this.portraitStatic_IMG = ImageMaster.loadImage("champResources/images/charSelect/charBG.png");
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
    }

 }


