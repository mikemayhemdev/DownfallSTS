package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = EnergyPanel.class, method = SpirePatch.CONSTRUCTOR)
public class EnergyPanelMovePatch {

    @SpirePostfixPatch
    public static void Postfix(EnergyPanel obj) {
        // //SlimeboundMod.logger.info("Energy panel move patch hit.");
        if (AbstractDungeon.player != null) {

            if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                obj.show_y = 130.0F * Settings.scale;
                //    //SlimeboundMod.logger.info("Energy panel move patch success");
            }
        } else {
            //  //SlimeboundMod.logger.info("Energy panel move patch: no character");
        }

    }
}
