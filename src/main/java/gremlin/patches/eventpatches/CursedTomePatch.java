package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

public class CursedTomePatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:CursedTome");

    @SpirePatch(clz = CursedTome.class, method = SpirePatch.CONSTRUCTOR)
    public static class TomeConstructior
    {
        public static void Postfix(CursedTome __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, CursedTome.class, "finalDmg");
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                ReflectionHacks.setPrivate(__instance, CursedTome.class, "finalDmg", dmg);
            }
        }
    }

    @SpirePatch(clz = CursedTome.class, method = "buttonEffect")
    public static class TomeOptionDamage
    {
        @SpireInsertPatch(
                rloc=43
        )
        public static void Insert(CursedTome __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, CursedTome.class, "finalDmg");
                __instance.imageEventText.updateDialogOption(0, strings.OPTIONS[0] + dmg + CursedTome.OPTIONS[6]);
            }
        }
    }

    @SpirePatch(clz = CursedTome.class, method = "buttonEffect")
    public static class TomeDamage
    {
        @SpireInsertPatch(
                rloc=49
        )
        public static void Insert(CursedTome __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, CursedTome.class, "finalDmg");
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(dmg);
            }
        }
    }
}
