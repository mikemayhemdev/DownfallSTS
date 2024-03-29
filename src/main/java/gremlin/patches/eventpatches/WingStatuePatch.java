package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

public class WingStatuePatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:WingStatue");

    @SpirePatch(clz = GoldenWing.class, method = SpirePatch.CONSTRUCTOR)
    public static class WingConstructior
    {
        public static void Postfix(GoldenWing __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, GoldenWing.class, "damage");
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                ReflectionHacks.setPrivate(__instance, GoldenWing.class, "damage", dmg);
//                __instance.imageEventText.updateDialogOption(0, strings.OPTIONS[0] + dmg + GoldenWing.OPTIONS[1]);
            }
        }
    }

    @SpirePatch(clz = GoldenWing.class, method = "buttonEffect")
    public static class WingDamage
    {
        @SpireInsertPatch(
                rloc=6
        )
        public static void Insert(GoldenWing __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, GoldenWing.class, "damage");
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(dmg);
            }
        }
    }
}
