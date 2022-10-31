package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import gremlin.characters.GremlinCharacter;

public class VampiresPatch {
    @SpirePatch(clz = Vampires.class, method = SpirePatch.CONSTRUCTOR)
    public static class VampiresConstructor
    {
        public static void Postfix(Vampires __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, Vampires.class, "maxHpLoss");
                dmg *= 5;
                ReflectionHacks.setPrivate(__instance, Vampires.class, "maxHpLoss", dmg);
                __instance.imageEventText.updateDialogOption(0, Vampires.OPTIONS[0] + dmg + Vampires.OPTIONS[1], new Bite());
            }
        }
    }
}
