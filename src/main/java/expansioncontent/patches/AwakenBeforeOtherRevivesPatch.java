package expansioncontent.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import expansioncontent.powers.AwakenDeathPower;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="damage",
        paramtypez={DamageInfo.class}
)
public class AwakenBeforeOtherRevivesPatch {
    @SpireInsertPatch(
            rloc=124
    )
    public static SpireReturn Insert(AbstractPlayer __instance, DamageInfo info) {
        if (__instance.currentHealth < 1) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                if (!__instance.hasRelic("Mark of the Bloom")) {
                    if (__instance.hasPower(AwakenDeathPower.POWER_ID)) {
                        ((AwakenDeathPower) (__instance.getPower(AwakenDeathPower.POWER_ID))).trigger(__instance);
                        return SpireReturn.Return(null);
                    }
                }
            }
        }
        return SpireReturn.Continue();
    }
}
