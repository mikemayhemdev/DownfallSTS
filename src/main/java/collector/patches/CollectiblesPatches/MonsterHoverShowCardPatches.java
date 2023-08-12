package collector.patches.CollectiblesPatches;


import basemod.ReflectionHacks;
import basemod.helpers.CardPowerTip;
import collector.CollectorChar;
import collector.CollectorCollection;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

import java.util.ArrayList;

public class MonsterHoverShowCardPatches {

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "renderTip"
    )
    public static class AddTip {
        @SpireInsertPatch(locator = AddCardTipLocator.class)
        public static void Insert(AbstractMonster __instance, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR) || !CollectorCollection.collection.isEmpty()) {
                ArrayList<PowerTip> tips = ReflectionHacks.getPrivateInherited(__instance, AbstractMonster.class, "tips");
                tips.add(new CardPowerTip(CollectorCollection.getCollectedCard(__instance)));
                ReflectionHacks.setPrivateInherited(__instance, AbstractCreature.class, "tips", tips);
            }
        }
    }

    private static class AddCardTipLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}