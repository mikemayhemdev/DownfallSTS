package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch(clz = AbstractCreature.class, method = "heal", paramtypez = {int.class, boolean.class})

public class HealPatch {

    @SpirePrefixPatch
    public static SpireReturn Prefix(AbstractCreature __instance, int healAmount, boolean showEffect) {
        return SpireReturn.Continue();
    }

}
