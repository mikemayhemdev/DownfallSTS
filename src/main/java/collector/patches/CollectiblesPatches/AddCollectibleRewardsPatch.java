package collector.patches.CollectiblesPatches;

import collector.CollectorChar;
import collector.CollectorCollection;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "die",
        paramtypez = {
                boolean.class
        }
)
public class AddCollectibleRewardsPatch {
    public static void Postfix(AbstractMonster __instance, boolean triggerRelics) {
        if (triggerRelics) {
            if (AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR) || !CollectorCollection.collection.isEmpty()) {
                CollectorCollection.collect(__instance);
            }
        }
    }
}
