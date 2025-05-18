package awakenedOne.patches;

import awakenedOne.powers.OnLoseEnergyPower;
import awakenedOne.relics.OnLoseEnergyRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "loseEnergy"
)
public class OnLoseEnergyPowerPatch {

    public static int EnergyLostThisCombat = 0;

    public static void Postfix(AbstractPlayer __instance, int e) {
        EnergyLostThisCombat = EnergyLostThisCombat + e;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnLoseEnergyPower) {
                ((OnLoseEnergyPower) p).LoseEnergyAction(e);
            }
        }

        for (AbstractRelic p : AbstractDungeon.player.relics) {
            if (p instanceof OnLoseEnergyRelic) {
                ((OnLoseEnergyRelic) p).LoseEnergyAction(e);
            }
        }
    }
    }