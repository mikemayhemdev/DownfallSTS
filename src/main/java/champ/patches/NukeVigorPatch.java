package champ.patches;

import champ.relics.PowerArmor;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class NukeVigorPatch {
    @SpirePatch(
            clz = VigorPower.class,
            method = "updateDescription"
    )
    public static class NukeVigor {
        @SpirePostfixPatch
        public static void Postfix(VigorPower instance) {
            if (AbstractDungeon.player.hasRelic(PowerArmor.ID)) {
                if (instance.amount > PowerArmor.CAP_RESOLVE_ETC) {
                    ((PowerArmor)(AbstractDungeon.player.getRelic(PowerArmor.ID))).onTrigger(instance.amount - PowerArmor.CAP_RESOLVE_ETC);
                    instance.amount = PowerArmor.CAP_RESOLVE_ETC;
                    instance.updateDescription();
                }
            }
        }
    }
}