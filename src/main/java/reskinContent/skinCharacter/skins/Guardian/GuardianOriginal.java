package reskinContent.skinCharacter.skins.Guardian;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import guardian.GuardianMod;
import reskinContent.skinCharacter.AbstractSkin;

public class GuardianOriginal extends AbstractSkin {

    public GuardianOriginal() {
        this.portraitStatic_IMG = ImageMaster.loadImage(GuardianMod.getResourcePath("charSelect/portrait.png"));
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
    }

}


