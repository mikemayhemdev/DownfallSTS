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
        HashMap<String, AbstractDailyMod> myMapS = (HashMap) ReflectionHacks.getPrivateStatic(ModHelper.class, "starterMods");
        HashMap<String, AbstractDailyMod> myMapG = (HashMap) ReflectionHacks.getPrivateStatic(ModHelper.class, "genericMods");
        HashMap<String, AbstractDailyMod> myMapD = (HashMap) ReflectionHacks.getPrivateStatic(ModHelper.class, "difficultyMods");
        myMapD.put(Hexed.ID, new Hexed());
        myMapS.put(Improvised.ID, new Improvised());
        myMapG.put(Jewelcrafting.ID, new Jewelcrafting());
        myMapD.put(WorldOfGoo.ID, new WorldOfGoo());
        ReflectionHacks.setPrivateStatic(ModHelper.class, "starterMods", myMapS);
        ReflectionHacks.setPrivateStatic(ModHelper.class, "genericMods", myMapG);
        ReflectionHacks.setPrivateStatic(ModHelper.class, "difficultyMods", myMapD);
    }
}
