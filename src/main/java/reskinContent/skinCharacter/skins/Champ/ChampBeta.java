package reskinContent.skinCharacter.skins.Champ;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

public class ChampBeta extends AbstractSkin {

    public ChampBeta() {
        this.portraitStatic_IMG = ImageMaster.loadImage(getAssetPath("portrait_beta_champ.png"));
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinChamp").TEXT[1];
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/ChampMod/" + this.getClass().getSimpleName() + "/" + filename);
    }
}