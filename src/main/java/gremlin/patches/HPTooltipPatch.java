package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import gremlin.characters.GremlinCharacter;
import javassist.CtBehavior;

import java.util.Map;

@SpirePatch(clz = TopPanel.class, method = "updateTips")
public class HPTooltipPatch
{
    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn patch(TopPanel __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            float TIP_OFF_X = ReflectionHacks.getPrivateStatic(TopPanel.class, "TIP_OFF_X");
            float TIP_Y = ReflectionHacks.getPrivateStatic(TopPanel.class, "TIP_Y");
            String[] LABEL = ReflectionHacks.getPrivateStatic(TopPanel.class, "LABEL");
            Map<String, Integer> gremlinHps = ((GremlinCharacter)AbstractDungeon.player).getAllGremlinHPs();
            final StringBuilder sb = new StringBuilder();
            gremlinHps.forEach((name, hp) -> {
                if (hp > 0) {
                    sb.append(name);
                    sb.append(": ");
                    sb.append(hp);
                    sb.append("/");
                    sb.append(AbstractDungeon.player.maxHealth);
                    sb.append(" NL ");
                }
                else if (hp == 0) {
                    sb.append(colorifyName(name, 'r'));
                    sb.append(": #r");
                    sb.append(hp);
                    sb.append("/");
                    sb.append(AbstractDungeon.player.maxHealth);
                    sb.append(" NL ");
                }
                else {
                    sb.append(colorifyName(name, 'p'));
                    sb.append(": #pTAKEN");
                    sb.append(" NL ");
                }
            });
            sb.setLength(sb.length() - 4);
            TipHelper.renderGenericTip((float) InputHelper.mX - TIP_OFF_X, TIP_Y, LABEL[3], sb.toString());
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

    private static String colorifyName(String s, char color) {
        StringBuilder sb = new StringBuilder();
        for (String part:s.split(" ")) {
            sb.append("#");
            sb.append(color);
            sb.append(part);
            sb.append(" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(TopPanel.class, "hpHb");
            return new int[]{LineFinder.findInOrder(ctBehavior, finalMatcher)[0]+1};
        }
    }
}
