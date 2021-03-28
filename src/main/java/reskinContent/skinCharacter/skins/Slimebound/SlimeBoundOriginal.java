package reskinContent.skinCharacter.skins.Slimebound;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;
import slimebound.SlimeboundMod;

public class SlimeBoundOriginal extends AbstractSkin {

    public SlimeBoundOriginal() {
        super();
        this.portraitStatic_IMG = ImageMaster.loadImage(SlimeboundMod.getResourcePath("charSelect/portrait.png"));

        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
    }
}


