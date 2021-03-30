package reskinContent.skinCharacter.skins.Slimebound;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

public class BetaSlimeBoss extends AbstractSkin {

    public BetaSlimeBoss() {
        super();

        this.portraitStatic_IMG = ImageMaster.loadImage(getAssetPath("portrait_beta_slimeboss.png"));
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinSlime").TEXT[1];
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/Slimebound/" + this.getClass().getSimpleName() + "/" + filename);
    }
}