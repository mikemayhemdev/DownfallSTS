package downfall.patches;

import automaton.EasyInfoDisplayPanel;
import automaton.FunctionHelper;
import champ.StanceHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theHexaghost.HexaMod;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "render",
        paramtypez = {SpriteBatch.class}
)
public class GlobalRenderSpecialHookPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
        if (HexaMod.renderFlames) {
            HexaMod.renderGhostflames(sb);
        }
        if (FunctionHelper.doStuff) { // TODO: Make this good
            FunctionHelper.render(sb);
        }
        StanceHelper.render(sb);
    }
}