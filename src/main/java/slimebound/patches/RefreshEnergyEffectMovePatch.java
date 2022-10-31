package slimebound.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = RefreshEnergyEffect.class, method = SpirePatch.CONSTRUCTOR)
public class RefreshEnergyEffectMovePatch {

    @SpirePostfixPatch
    public static void Postfix(RefreshEnergyEffect obj) {
        // //SlimeboundMod.logger.info("Energy panel VFX move patch hit.");
        if (AbstractDungeon.player != null) {

            if (AbstractDungeon.player instanceof SlimeboundCharacter) {

                TextureAtlas.AtlasRegion image = (TextureAtlas.AtlasRegion) ReflectionHacks.getPrivate(obj, RefreshEnergyEffect.class, "img");
                ReflectionHacks.setPrivate(obj, RefreshEnergyEffect.class, "y", 130F * Settings.scale - (float) image.packedHeight / 2.0F);

                //    //SlimeboundMod.logger.info("Energy panel VFX move patch success");
            }
        } else {
            // //SlimeboundMod.logger.info("Energy panel move VFX patch: no character");
        }

    }
}
