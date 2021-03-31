package reskinContent.skinCharacter.skins.Snecko;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

public class BetaSnecko extends AbstractSkin {

    public BetaSnecko() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinSnecko").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinSnecko").EXTRA_TEXT[1];
        this.portraitStatic_IMG = ImageMaster.loadImage(getAssetPath("portrait_beta_snecko.png"));

        this.SHOULDER1 = "sneckomodResources/images/char/shoulder.png";
        this.SHOULDER2 = "sneckomodResources/images/char/shoulderR.png";
        this.CORPSE = "sneckomodResources/images/char/corpse.png";
        this.atlasURL = "reskinContent/img/SneckoMod/BetaSnecko/animation/BetaSnecko.atlas";
        this.jsonURL = "reskinContent/img/SneckoMod/BetaSnecko/animation/BetaSnecko.json";
        this.renderscale = 3.0f;
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/SneckoMod/" + this.getClass().getSimpleName() + "/" + filename);
    }

}


