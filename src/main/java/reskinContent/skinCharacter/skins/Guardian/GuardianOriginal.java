package reskinContent.skinCharacter.skins.Guardian;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;

public class GuardianOriginal extends AbstractSkin {

    public GuardianOriginal() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString("guardianmod:Guardian").TEXT[0];
        this.portraitStatic_IMG = ImageMaster.loadImage("guardianResources/GuardianImages/charSelect/portrait.png");

        this.SHOULDER1 = "guardianResources/GuardianImages/char/shoulder.png";
        this.SHOULDER2 = "guardianResources/GuardianImages/char/shoulderR.png";
        this.CORPSE = "guardianResources/GuardianImages/char/corpse.png";
        this.atlasURL = "guardianResources/GuardianImages/char/skeleton.atlas";
        this.jsonURL = "guardianResources/GuardianImages/char/skeleton.json";
        this.renderscale = 2.5F;
    }
}


