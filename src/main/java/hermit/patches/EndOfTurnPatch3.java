package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import hermit.HermitMod;


@SpirePatch(
        clz= GameActionManager.class,
        method="clear"
)

public class EndOfTurnPatch3 {

    @SpirePrefixPatch
    public static SpireReturn Clear(GameActionManager m)
    {
            System.out.println("Setting bool to false");
            EndOfTurnPatch.deadon_counter = 0;

        return SpireReturn.Continue();
    }

}
