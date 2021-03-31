package reskinContent.skinCharacter;

import champ.ChampChar;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.Champ.*;
import reskinContent.vfx.ReskinUnlockedTextEffect;


public class ChampSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("champ:theChamp").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new ChampOriginal(),
            new ChampSister(),
            new BetaChamp()
    };

    public ChampSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == ChampChar.Enums.THE_CHAMP && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(4));
            this.reskinUnlock = true;
        }
    }
}


