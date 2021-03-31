package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.Hexaghost.BetaHexaghost;
import reskinContent.skinCharacter.skins.Hexaghost.HexaghostOriginal;
import reskinContent.skinCharacter.skins.Hexaghost.Hexago;
import reskinContent.vfx.ReskinUnlockedTextEffect;
import theHexaghost.TheHexaghost;


public class HexaghostSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("hexamod:theHexaghost").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new BetaHexaghost(),
            new Hexago()
    };

    public HexaghostSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == TheHexaghost.Enums.THE_SPIRIT && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(2));
            this.reskinUnlock = true;
        }
    }
}


