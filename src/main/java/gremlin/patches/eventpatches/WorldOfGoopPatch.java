package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

public class WorldOfGoopPatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:WorldOfGoop");

    @SpirePatch(clz = GoopPuddle.class, method = SpirePatch.CONSTRUCTOR)
    public static class GoopConstructior
    {
        public static void Postfix(GoopPuddle __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, GoopPuddle.class, "damage");
                dmg = (dmg + 4) / 5; // Divided by 5 rounded up
                ReflectionHacks.setPrivate(__instance, GoopPuddle.class, "damage", dmg);
                int gold = (int) ReflectionHacks.getPrivate(__instance, GoopPuddle.class, "gold");
                __instance.imageEventText.updateDialogOption(0, GoopPuddle.OPTIONS[0] + gold + strings.OPTIONS[0] + dmg + GoopPuddle.OPTIONS[2]);
            }
        }
    }

    @SpirePatch(clz = GoopPuddle.class, method = "buttonEffect")
    public static class GoopDamage
    {
        @SpireInsertPatch(
                rloc=6
        )
        public static void Insert(GoopPuddle __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, GoopPuddle.class, "damage");
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(dmg);
            }
        }
    }
}
