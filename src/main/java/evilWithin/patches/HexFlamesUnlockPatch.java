package evilWithin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.unlock.UnlockCharacterScreen;
import javassist.CtBehavior;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;

@SpirePatch(
        clz = UnlockCharacterScreen.class,
        method = "render",
        paramtypez = {SpriteBatch.class}
)
public class HexFlamesUnlockPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(UnlockCharacterScreen __instance, SpriteBatch sb) {
        if (__instance.unlock.player instanceof TheHexaghost){
            TheHexaghost hex = (TheHexaghost)__instance.unlock.player;
            hex.myBody.render(sb);
        }

    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            //Matcher finalMatcher = new Matcher.FieldAccessMatcher(UnlockTracker.class, "isCharacterLocked");
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(UnlockCharacterScreen.class, "unlock");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
        }
    }
}