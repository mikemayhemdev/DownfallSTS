package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

public class ScrapOozePatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:ScrapOoze");
    private static int realDmg = 0;

    @SpirePatch(clz = ScrapOoze.class, method = SpirePatch.CONSTRUCTOR)
    public static class OozeConstructior
    {
        public static void Postfix(ScrapOoze __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, ScrapOoze.class, "dmg");
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                int relicObtainChance = (int) ReflectionHacks.getPrivate(__instance, ScrapOoze.class, "relicObtainChance");
                __instance.imageEventText.updateDialogOption(0, strings.OPTIONS[0] + dmg + ScrapOoze.OPTIONS[1] + relicObtainChance + ScrapOoze.OPTIONS[2]);
            }
        }
    }

    @SpirePatch(clz = ScrapOoze.class, method = "buttonEffect")
    public static class OozePreDamage
    {
        @SpireInsertPatch(
                rloc=4
        )
        public static void Insert(ScrapOoze __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, ScrapOoze.class, "dmg");
                realDmg = dmg;
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(dmg);
                ReflectionHacks.setPrivate(__instance, ScrapOoze.class, "dmg", dmg);
            }
        }
    }

    @SpirePatch(clz = ScrapOoze.class, method = "buttonEffect")
    public static class OozePostDamage
    {
        @SpireInsertPatch(
                rloc=7
        )
        public static void Insert(ScrapOoze __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                ReflectionHacks.setPrivate(__instance, ScrapOoze.class, "dmg", realDmg);
            }
        }
    }

    @SpirePatch(clz = ScrapOoze.class, method = "buttonEffect")
    public static class OozeOption
    {
        @SpireInsertPatch(
                rloc=28
        )
        public static void Insert(ScrapOoze __instance, int buttonPressed) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, ScrapOoze.class, "dmg");
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                int relicObtainChance = (int) ReflectionHacks.getPrivate(__instance, ScrapOoze.class, "relicObtainChance");
                __instance.imageEventText.updateDialogOption(0, strings.OPTIONS[1] + dmg + ScrapOoze.OPTIONS[1] + relicObtainChance + ScrapOoze.OPTIONS[2]);
            }
        }
    }
}
