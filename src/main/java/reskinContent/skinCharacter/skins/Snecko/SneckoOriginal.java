package reskinContent.skinCharacter.skins.Snecko;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;
import sneckomod.TheSnecko;

public class SneckoOriginal extends AbstractSkin {

    public SneckoOriginal() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(TheSnecko.ID).TEXT[0];
        this.portraitStatic_IMG = ImageMaster.loadImage("sneckomodResources/images/charSelect/portrait.png");

        this.SHOULDER1 = "sneckomodResources/images/char/shoulder.png";
        this.SHOULDER2 =  "sneckomodResources/images/char/shoulderR.png";
        this.CORPSE = "sneckomodResources/images/char/corpse.png";
        this.atlasURL = "sneckomodResources/images/char/skeleton.atlas";
        this.jsonURL = "sneckomodResources/images/char/skeleton.json";
        this.renderscale = 1.2f;
    }

}


