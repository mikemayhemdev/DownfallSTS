package awakenedOne.patches;

import awakenedOne.powers.OnLoseEnergyPower;
import awakenedOne.relics.OnLoseEnergyRelic;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

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

        for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
            if (!m2.isDead && !m2.isDying) {
                for (AbstractPower p : m2.powers) {
                    if (p instanceof OnLoseEnergyPower) {
                        ((OnLoseEnergyPower) p).LoseEnergyAction(e);
                    }
                }
            }

            for (AbstractRelic p : AbstractDungeon.player.relics) {
                if (p instanceof OnLoseEnergyRelic) {
                    ((OnLoseEnergyRelic) p).LoseEnergyAction(e);
                }
            }
        }
    }
}