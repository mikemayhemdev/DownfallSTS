package reskinContent.skinCharacter.skins.Hexaghost;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

public class HexaghostBeta extends AbstractSkin {

    public HexaghostBeta() {
        this.portraitStatic_IMG = ImageMaster.loadImage(getAssetPath("portrait_beta_hexaghost.png"));
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinHexaghost").TEXT[1];
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/HexaghostMod/" + this.getClass().getSimpleName() + "/" + filename);
    }
}