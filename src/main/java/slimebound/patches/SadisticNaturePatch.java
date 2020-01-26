package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;
import slimebound.relics.AggressiveSlimeRelic;

import java.lang.reflect.Field;

@SpirePatch(clz = SadisticNature.class,
        method = SpirePatch.CONSTRUCTOR)

public class SadisticNaturePatch {
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());



    @SpirePostfixPatch
    public static void Patch(SadisticNature SadisticNature_instance) {




       // logger.info("Sadistic Nature Patch hit");
        if (AbstractDungeon.isPlayerInDungeon()) {
          //  logger.info("Sadistic Nature Patch player is in dungeon");
            if ((AbstractDungeon.player instanceof SlimeboundCharacter)) {

               // logger.info("Sadistic Nature reducing cost");

                SadisticNature_instance.cost = 2;
                SadisticNature_instance.costForTurn = 2;

            }
        }

    }
}

