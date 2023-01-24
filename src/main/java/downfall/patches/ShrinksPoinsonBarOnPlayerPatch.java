package downfall.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import javassist.CtBehavior;

// Make the Green poison bar on player shrinks into shortest like how is's performed on enemies when the player gets intangible.
public class ShrinksPoinsonBarOnPlayerPatch {

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderRedHealthBar"
    )
    public static class ShrinksPoinsonBarOnPlayer{

        @SpireInsertPatch( // inserts after int poisonAmt = (getPower("Poison")).amount;
                locator = Locator.class,
                localvars = {"poisonAmt"}
        )
        public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y,@ByRef int[] poisonAmt) {
            if (poisonAmt[0] > 0 && AbstractDungeon.player.hasPower(IntangiblePlayerPower.POWER_ID)) {
                poisonAmt[0] = 1;
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "hasPower");
                return new int[] { LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1] };
            }
        }

    }

}
