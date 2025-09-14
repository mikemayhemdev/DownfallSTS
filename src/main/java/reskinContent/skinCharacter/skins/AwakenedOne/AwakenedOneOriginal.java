package reskinContent.skinCharacter.skins.AwakenedOne;

import automaton.AutomatonChar;
import awakenedOne.AwakenedOneChar;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import downfall.util.TextureLoader;
import reskinContent.skinCharacter.AbstractSkin;

public class AwakenedOneOriginal extends AbstractSkin {

    public AwakenedOneOriginal() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(AwakenedOneChar.ID).TEXT[0];
        this.portraitStatic_IMG = TextureLoader.getTexture("awakenedResources/images/charSelect/charBG.png");
        this.SHOULDER1 = "awakenedResources/images/mainChar/shoulder.png";
        this.SHOULDER2 = "awakenedResources/images/mainChar/shoulderR.png";
        this.CORPSE = "awakenedResources/images/mainChar/corpse.png";
        this.atlasURL = "awakenedResources/images/mainChar/awakened.atlas";
        this.jsonURL = "awakenedResources/images/mainChar/awakened.json";
        this.renderscale = 1.2f;
    }
}


