package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.daily.mods.NightTerrors;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= CampfireSleepEffect.class,
        method=SpirePatch.CONSTRUCTOR
)
public class CampfireRestPatch {

    public static void Postfix(CampfireSleepEffect __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            GremlinCharacter gremlinMob = (GremlinCharacter)(AbstractDungeon.player);
            if (ModHelper.isModEnabled(NightTerrors.ID)) {
                gremlinMob.mobState.setToMax(gremlinMob.maxHealth);
            } else {
                int toHeal = (Integer)ReflectionHacks.getPrivate(__instance, CampfireSleepEffect.class, "healAmount");
                gremlinMob.mobState.campfireHeal(toHeal, gremlinMob.maxHealth);
            }
        }
    }
}
