package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.patches.GuardianEnum;
import reskinContent.skinCharacter.skins.Champ.ChampOriginal;
import reskinContent.skinCharacter.skins.Champ.ChampSister;
import reskinContent.skinCharacter.skins.Guardian.*;
import reskinContent.vfx.ReskinUnlockedTextEffect;


public class GuardianSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("Guardian").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new GuardianOriginal(),
            new GuardianChan()
    };

    public GuardianSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(0));
            this.reskinUnlock = true;
        }
    }
}


