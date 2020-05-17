package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.stances.AbstractStance;
import guardian.characters.DefensiveMode;

@SpirePatch(clz = AbstractStance.class, method = "getStanceFromName")
public class StancePatch {
    @SpirePrefixPatch
    public static SpireReturn<AbstractStance> returnStance(String name) {
        if (name.equals(DefensiveMode.STANCE_ID)) {
            return SpireReturn.Return(new DefensiveMode());
        }
        return SpireReturn.Continue();
    }
}