package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import reskinContent.skinCharacter.skins.Snecko.BetaSnecko;
import reskinContent.skinCharacter.skins.Snecko.SSSSnecko;
import reskinContent.skinCharacter.skins.Snecko.SneckoOriginal;
import reskinContent.vfx.ReskinUnlockedTextEffect;


public class SneckoSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("sneckomod:theSnecko").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new SneckoOriginal(),
            new SSSSnecko(),
            new BetaSnecko()
    };

    public SneckoSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_SNECKO && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(3));
            this.reskinUnlock = true;
        }
    }
}


