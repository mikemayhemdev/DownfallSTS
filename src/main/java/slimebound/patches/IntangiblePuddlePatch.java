package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = IntangiblePlayerPower.class, method = "atEndOfRound")
public class IntangiblePuddlePatch {

    @SpirePostfixPatch
    public static void Postfix(IntangiblePlayerPower obj) {
        ////SlimeboundMod.logger.info("Intangible patch hit." + obj.amount);

        if (obj.amount == 1) {
            ////SlimeboundMod.logger.info("Intangible patch hit. intangible is 0");


            if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                ////SlimeboundMod.logger.info("Intangible patch hit. removing puddle");
                ((SlimeboundCharacter) AbstractDungeon.player).removePuddleForm();
            }
        }
    }
}
