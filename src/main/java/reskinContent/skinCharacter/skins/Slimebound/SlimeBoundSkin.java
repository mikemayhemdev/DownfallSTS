package reskinContent.skinCharacter.skins.Slimebound;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import reskinContent.skinCharacter.AbstractSkin;
import reskinContent.skinCharacter.AbstractSkinCharacter;
import reskinContent.skinCharacter.skins.Slimebound.BetaSlimeBoss;
import reskinContent.skinCharacter.skins.Slimebound.Slaifu;
import reskinContent.skinCharacter.skins.Slimebound.SlimeBoundOriginal;
import reskinContent.vfx.ReskinUnlockedTextEffect;

public class SlimeBoundSkin extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("Slimebound").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new SlimeBoundOriginal(),
            new Slaifu(),
            new BetaSlimeBoss()
    };

    public SlimeBoundSkin() {
        super(ID, SKINS);
    }

    @Override
    public void checkUnlock() {
        System.out.println("================您跑了吗3");
        if (AbstractDungeon.player.chosenClass == downfallMod.Enums.SLIMEBOUND && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(1));
            this.reskinUnlock = true;
        }
    }
}


