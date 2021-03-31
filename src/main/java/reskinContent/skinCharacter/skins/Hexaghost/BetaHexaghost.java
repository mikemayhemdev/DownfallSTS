package reskinContent.skinCharacter.skins.Hexaghost;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;
import theHexaghost.TheHexaghost;

public class BetaHexaghost extends AbstractSkin {

    public BetaHexaghost() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(TheHexaghost.ID).TEXT[0];
        this.portraitStatic_IMG = ImageMaster.loadImage("hexamodResources/images/charSelect/charBG.png");

        this.SHOULDER1 = "hexamodResources/images/char/mainChar/shoulder.png";;
        this.SHOULDER2 = "hexamodResources/images/char/mainChar/shoulderR.png";
        this.CORPSE = "hexamodResources/images/char/mainChar/corpse.png";
        this.atlasURL = "reskinContent/img/HexaghostMod/BetaHexaghost/animation/BetaHexaghost.atlas";
        this.jsonURL = "reskinContent/img/HexaghostMod/BetaHexaghost/animation/BetaHexaghost.json";
        this.renderscale = 1.0F;
    }
}


