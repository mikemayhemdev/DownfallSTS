package reskinContent.skinCharacter.skins.Snecko;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;

public class SneckoOriginal extends AbstractSkin {

    public SneckoOriginal() {
        this.portraitStatic_IMG = ImageMaster.loadImage("sneckomodResources/images/charSelect/portrait.png");
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
    }

}


