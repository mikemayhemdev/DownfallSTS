package reskinContent.skinCharacter.skins.Hexaghost;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;
import theHexaghost.TheHexaghost;

public class BetaHexaghost extends AbstractSkin {

    public BetaHexaghost() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinHexaghost").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinHexaghost").EXTRA_TEXT[1];
        this.portraitStatic_IMG = ImageMaster.loadImage("reskinContent/img/HexaghostMod/BetaHexaghost/portrait_beta.png");

        this.SHOULDER1 = "hexamodResources/images/char/mainChar/shoulder.png";;
        this.SHOULDER2 = "hexamodResources/images/char/mainChar/shoulderR.png";
        this.CORPSE = "hexamodResources/images/char/mainChar/corpse.png";
        this.atlasURL = "reskinContent/img/HexaghostMod/BetaHexaghost/animation/BetaHexaghost.atlas";
        this.jsonURL = "reskinContent/img/HexaghostMod/BetaHexaghost/animation/BetaHexaghost.json";
        this.renderscale = 1.0F;
    }
}


