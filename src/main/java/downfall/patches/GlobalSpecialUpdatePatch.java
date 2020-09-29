package downfall.patches;

import champ.StanceHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "update"
)
public class GlobalSpecialUpdatePatch {
    public static void Prefix(EnergyPanel __instance) {
        if (HexaMod.renderFlames) {
            GhostflameHelper.update();
        }
        if (StanceHelper.hitboxStance == null) StanceHelper.init();
        StanceHelper.update();
    }
}