package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import hermit.HermitMod;


@SpirePatch(
        clz= AbstractPlayer.class,
        method="applyStartOfTurnPreDrawCards"
)

public class EndOfTurnPatch {
    public static int deadon_counter = 0;

    @SpirePrefixPatch
    public static SpireReturn EOT(AbstractPlayer p)
    {
        deadon_counter = 0;

        return SpireReturn.Continue();
    }

}
