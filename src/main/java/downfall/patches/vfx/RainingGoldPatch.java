package downfall.patches.vfx;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import downfall.patches.EvilModeCharacterSelect;
import downfall.vfx.RainingSoulsEffect;

@SpirePatch(
        clz = RainingGoldEffect.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {int.class}
)
public class RainingGoldPatch {

    @SpirePrefixPatch
    public static SpireReturn<?> Prefix(RainingGoldEffect __instance, int amount) {
        if (EvilModeCharacterSelect.evilMode){
            AbstractDungeon.effectList.add(new RainingSoulsEffect(amount));
            AbstractDungeon.effectList.remove(__instance);
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();

    }
}
