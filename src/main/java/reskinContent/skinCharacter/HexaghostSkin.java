package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.patches.GuardianEnum;
import org.apache.commons.codec.binary.Hex;
import reskinContent.skinCharacter.skins.Guardian.*;
import reskinContent.skinCharacter.skins.Hexaghost.HexaghostOriginal;
import reskinContent.skinCharacter.skins.Hexaghost.Hexago;
import reskinContent.vfx.ReskinUnlockedTextEffect;
import theHexaghost.TheHexaghost;


public class HexaghostSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("hexamod:theHexaghost").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new HexaghostOriginal(),
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


