package downfall.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ModHelper;
import downfall.dailymods.Hexed;
import downfall.dailymods.Improvised;
import downfall.dailymods.Jewelcrafting;
import downfall.dailymods.WorldOfGoo;

import java.util.HashMap;

@SpirePatch(
        clz = ModHelper.class,
        method = "initialize"
)
public class ModHelperPatch {
    public static void Postfix() {
        HashMap<String, AbstractDailyMod> myMap = (HashMap) ReflectionHacks.getPrivateStatic(ModHelper.class, "genericMods");
        myMap.put(Hexed.ID, new Hexed());
        myMap.put(Improvised.ID, new Improvised());
        myMap.put(Jewelcrafting.ID, new Jewelcrafting());
        myMap.put(WorldOfGoo.ID, new WorldOfGoo());
        ReflectionHacks.setPrivateStatic(ModHelper.class, "genericMods", myMap);
    }
}
