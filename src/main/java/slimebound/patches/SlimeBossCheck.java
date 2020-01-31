package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = SlimeBoss.class, method = "usePreBattleAction")
public class SlimeBossCheck {

    public static void Prefix(SlimeBoss sb) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            ((SlimeboundCharacter) AbstractDungeon.player).foughtSlimeBoss = true;
            //  SlimeboundMod.logger.info("Slimebound Character has fought the Slime Boss.");

        }
    }
}