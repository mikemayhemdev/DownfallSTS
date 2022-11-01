package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import reskinContent.skinCharacter.skins.Champ.BetaChamp;
import reskinContent.skinCharacter.skins.Champ.ChampOriginal;
import reskinContent.skinCharacter.skins.Champ.ChampSister;
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
        if (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_CHAMP && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(4));
            this.reskinUnlock = true;
        }
    }
}


