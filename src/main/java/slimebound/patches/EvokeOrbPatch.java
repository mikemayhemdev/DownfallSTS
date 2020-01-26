package slimebound.patches;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz= EvokeSpecificOrbAction.class,method="update")
public class EvokeOrbPatch {

    public static SpireReturn<Void> Prefix(EvokeSpecificOrbAction obj) {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            return SpireReturn.Return(null);
        } else {

            return SpireReturn.Continue();
        }
    }
}
