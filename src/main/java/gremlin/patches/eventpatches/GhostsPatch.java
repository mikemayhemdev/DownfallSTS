package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Ghosts;
import gremlin.characters.GremlinCharacter;

public class GhostsPatch {
    @SpirePatch(clz = Ghosts.class, method = SpirePatch.CONSTRUCTOR)
    public static class GhostsConstructior
    {
        public static void Postfix(Ghosts __instance) {
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                int dmg = (int) ReflectionHacks.getPrivate(__instance, Ghosts.class, "hpLoss");
                dmg *= 5;
                ReflectionHacks.setPrivate(__instance, Ghosts.class, "hpLoss", dmg);

                if (AbstractDungeon.ascensionLevel >= 15) {
                    __instance.imageEventText.updateDialogOption(0, Ghosts.OPTIONS[3] + dmg + Ghosts.OPTIONS[1], new Apparition());
                } else {
                    __instance.imageEventText.updateDialogOption(0, Ghosts.OPTIONS[0] + dmg + Ghosts.OPTIONS[1], new Apparition());
                }
            }
        }
    }
}
