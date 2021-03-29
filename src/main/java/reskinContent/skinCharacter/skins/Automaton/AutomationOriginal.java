package reskinContent.skinCharacter.skins.Automaton;

import automaton.AutomatonChar;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.skinCharacter.AbstractSkin;

public class AutomationOriginal extends AbstractSkin {

    public AutomationOriginal() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(AutomatonChar.ID).TEXT[0];
        this.portraitStatic_IMG = ImageMaster.loadImage("bronzeResources/images/charSelect/charBG.png");
        this.SHOULDER1 = "bronzeResources/images/char/mainChar/shoulder.png";
        this.SHOULDER2 = "bronzeResources/images/char/mainChar/shoulderR.png";
        this.CORPSE = "bronzeResources/images/char/mainChar/corpse.png";
        this.atlasURL = "bronzeResources/images/char/mainChar/bronze.atlas";
        this.jsonURL = "bronzeResources/images/char/mainChar/bronze.json";
        this.renderscale = 1.2f;
    }
}


