package collector.patches.TorchHeadPatches;

import collector.Interfaces.RecoloredPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.HashMap;

public class RecoloredPowerPatch {
    static HashMap<Integer, Color> indexColorMap = new HashMap<>();
    public static HashMap<PowerTip, Color> tipColorMap = new HashMap<>();

    @SpirePatch(clz = AbstractPlayer.class, method = "renderPowerTips")

    public static class ColorYouOrDragonPatch {
        @SpireInsertPatch(locator = Locator1.class)
        public static void Insert1(AbstractPlayer __instance, SpriteBatch sb, AbstractPower ___p, ArrayList<PowerTip> ___tips) {
            if (___p instanceof RecoloredPower) {
                int index = ___tips.size();
                indexColorMap.put(index, ((RecoloredPower) ___p).getIconColor());
            }
        }

        private static class Locator1 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                ArrayList<Matcher> prerequisites = new ArrayList<Matcher>() {
                    {
                        add(new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers"));
                    }
                };
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
                return LineFinder.findAllInOrder(ctMethodToPatch, prerequisites, finalMatcher);
            }
        }

        @SpireInsertPatch(locator = Locator2.class)
        public static void Insert2(AbstractPlayer __instance, SpriteBatch sb, ArrayList<PowerTip> ___tips) {
            int index = 0;
            for (PowerTip tip : ___tips) {
                if (indexColorMap.containsKey(index)) {
                    tipColorMap.put(tip, indexColorMap.get(index));
                }
                index++;
            }

            indexColorMap.clear();
            index = 0;
        }

        private static class Locator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = TipHelper.class, method = "renderPowerTips")
    public static class ProperAlphaRenderPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(float x, float y, SpriteBatch sb, ArrayList<PowerTip> powerTips, PowerTip ___tip) {
            if (tipColorMap.containsKey(___tip)) {
                sb.setColor(tipColorMap.get(___tip));
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
