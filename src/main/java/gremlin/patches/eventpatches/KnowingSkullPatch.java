package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.KnowingSkull;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

public class KnowingSkullPatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:KnowingSkull");

    @SpirePatch(clz = KnowingSkull.class, method = SpirePatch.CONSTRUCTOR)
    public static class SkullConstructior
    {
        public static void Postfix(KnowingSkull __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, KnowingSkull.class, "hpCost");
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                ReflectionHacks.setPrivate(__instance, KnowingSkull.class, "hpCost", dmg);
            }
        }
    }

    @SpirePatch(clz = KnowingSkull.class, method = "buttonEffect")
    public static class SkullOptionDamage
    {
        @SpireInsertPatch(
                rloc=8
        )
        public static void Insert(KnowingSkull __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, KnowingSkull.class, "hpCost");
                __instance.imageEventText.updateDialogOption(0, strings.OPTIONS[2] + dmg + KnowingSkull.OPTIONS[1]);
                __instance.imageEventText.updateDialogOption(1, KnowingSkull.OPTIONS[5] + 90 + strings.OPTIONS[3] + dmg + KnowingSkull.OPTIONS[1]);
                __instance.imageEventText.updateDialogOption(2, strings.OPTIONS[1] + dmg + KnowingSkull.OPTIONS[1]);
                __instance.imageEventText.updateDialogOption(3, strings.OPTIONS[4] + dmg + KnowingSkull.OPTIONS[1]);
            }
        }
    }

    @SpirePatch(clz = KnowingSkull.class, method = "buttonEffect")
    public static class SkullDamage
    {
        @SpireInsertPatch(
                rloc=12
        )
        public static void Insert(KnowingSkull __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, KnowingSkull.class, "hpCost");
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(dmg);
            }
        }
    }
}
