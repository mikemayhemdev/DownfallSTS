 package reskinContent.skinCharacter.skins.Automaton;


 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.skinCharacter.AbstractSkin;

 public  class AutomationOriginal extends AbstractSkin {

    public AutomationOriginal() {
        this.portraitStatic_IMG = ImageMaster.loadImage("bronzeResources/images/charSelect/charBG.png");
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
    }

 }


