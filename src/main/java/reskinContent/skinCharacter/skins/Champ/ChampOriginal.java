package reskinContent.skinCharacter.skins.Champ;

import champ.ChampChar;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;

public class ChampOriginal extends AbstractSkin {

    public ChampOriginal() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(ChampChar.ID).TEXT[0];
        this.portraitStatic_IMG = ImageMaster.loadImage("champResources/images/charSelect/charBG.png");
        this.SHOULDER1 = "champResources/images/char/mainChar/shoulder.png";
        this.SHOULDER2 = "champResources/images/char/mainChar/shoulderR.png";
        this.CORPSE = "champResources/images/char/mainChar/corpse.png";
        this.atlasURL = "champResources/images/char/mainChar/champ.atlas";
        this.jsonURL = "champResources/images/char/mainChar/champ.json";
        this.renderscale = 1.2f;
    }
}


