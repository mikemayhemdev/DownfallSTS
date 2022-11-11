package reskinContent.skinCharacter.skins.Hexaghost;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import reskinContent.skinCharacter.AbstractSkin;
import reskinContent.skinCharacter.AbstractSkinCharacter;
import reskinContent.skinCharacter.skins.Hexaghost.BetaHexaghost;
import reskinContent.skinCharacter.skins.Hexaghost.HexaghostOriginal;
import reskinContent.skinCharacter.skins.Hexaghost.Hexago;
import reskinContent.vfx.ReskinUnlockedTextEffect;


public class HexaghostSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("hexamod:theHexaghost").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new HexaghostOriginal(),
            new Hexago(),
            new BetaHexaghost()
    };

    public HexaghostSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_SPIRIT && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(2));
            this.reskinUnlock = true;
        }
    }
}


