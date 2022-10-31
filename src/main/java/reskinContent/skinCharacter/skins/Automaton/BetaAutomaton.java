package reskinContent.skinCharacter.skins.Automaton;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

public class BetaAutomaton extends AbstractSkin {

    public BetaAutomaton() {
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinBronze").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinBronze").EXTRA_TEXT[1];
        this.portraitStatic_IMG = ImageMaster.loadImage(getAssetPath("portrait_beta_automaton.png"));

        this.SHOULDER1 = "bronzeResources/images/char/mainChar/shoulder.png";
        this.SHOULDER2 = "bronzeResources/images/char/mainChar/shoulderR.png";
        this.CORPSE = "bronzeResources/images/char/mainChar/corpse.png";
        this.atlasURL = "reskinContent/img/BronzeMod/BetaAutomaton/animation/BetaAutomaton.atlas";
        this.jsonURL = "reskinContent/img/BronzeMod/BetaAutomaton/animation/BetaAutomaton.json";
        this.renderscale = 1.6f;
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/BronzeMod/" + this.getClass().getSimpleName() + "/" + filename);
    }
}


