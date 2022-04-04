package reskinContent.skinCharacter.skins.TimeEater;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;
import sneckomod.TheSnecko;
import timeeater.TimeEaterChar;

public class TimeEaterOriginal extends AbstractSkin {

    public TimeEaterOriginal() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(TimeEaterChar.ID).TEXT[0];
        this.portraitStatic_IMG = ImageMaster.loadImage("timeResources/images/charSelect/portrait.png");

        this.SHOULDER1 = "timeResources/images/char/mainChar/shoulder.png";
        this.SHOULDER2 =  "timeResources/images/char/mainChar/shoulderR.png";
        this.CORPSE = "timeResources/images/char/mainChar/corpse.png";
        this.atlasURL = "timeResources/images/char/mainChar/skeleton.atlas";
        this.jsonURL = "timeResources/images/char/mainChar/skeleton.json";
        this.renderscale = 1.2f;
    }

}


