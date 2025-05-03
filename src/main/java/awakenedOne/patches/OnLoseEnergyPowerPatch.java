package awakenedOne.patches;

import awakenedOne.powers.OnLoseEnergyPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "loseEnergy"
)
public class OnLoseEnergyPowerPatch {

    public static void Postfix(AbstractPlayer __instance, int e) {
        if (EnergyPanel.totalCount > 0) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnLoseEnergyPower) {
                    ((OnLoseEnergyPower) p).LoseEnergyAction(e);
                }
            }
        }
    }
}