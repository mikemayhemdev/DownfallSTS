package reskinContent.skinCharacter;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.Slimebound.BetaSlimeBoss;
import reskinContent.skinCharacter.skins.Slimebound.Slaifu;
import reskinContent.skinCharacter.skins.Slimebound.SlimeBoundOriginal;
import reskinContent.vfx.ReskinUnlockedTextEffect;
import slimebound.patches.SlimeboundEnum;

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
        if (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(1));
            this.reskinUnlock = true;
        }
    }
}


