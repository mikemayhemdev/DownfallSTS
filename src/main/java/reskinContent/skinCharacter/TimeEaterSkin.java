package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.Snecko.BetaSnecko;
import reskinContent.skinCharacter.skins.Snecko.SSSSnecko;
import reskinContent.skinCharacter.skins.Snecko.SneckoOriginal;
import reskinContent.skinCharacter.skins.TimeEater.TimeEaterOriginal;
import reskinContent.vfx.ReskinUnlockedTextEffect;
import sneckomod.TheSnecko;
import timeeater.TimeEaterChar;


public class TimeEaterSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("time:theTimeEater").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new TimeEaterOriginal()
    };

    public TimeEaterSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == TimeEaterChar.Enums.THE_TIME_EATER && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(3));
            this.reskinUnlock = true;
        }
    }
}


