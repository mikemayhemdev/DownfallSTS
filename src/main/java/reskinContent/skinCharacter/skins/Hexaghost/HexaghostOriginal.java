 package reskinContent.skinCharacter.skins.Hexaghost;


 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.skinCharacter.AbstractSkin;

 public  class HexaghostOriginal extends AbstractSkin {

    public HexaghostOriginal() {
        this.portraitStatic_IMG = ImageMaster.loadImage("hexamodResources/images/charSelect/charBG.png");
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
    }

 }


