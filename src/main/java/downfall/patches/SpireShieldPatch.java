package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import slimebound.powers.PotencyPower;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;
import theHexaghost.powers.EnhancePower;


//thanks to ocean for the code from cranky mod
@SpirePatch(clz=SpireShield.class, method="takeTurn")
public class SpireShieldPatch {
    @SpireInsertPatch(rloc=8)
    public static SpireReturn<Void> Insert(SpireShield __instance) {
        if ((AbstractDungeon.player instanceof SlimeboundCharacter || AbstractDungeon.player instanceof TheHexaghost || AbstractDungeon.player instanceof TheSnecko || AbstractDungeon.player instanceof GuardianCharacter)) {

            if (AbstractDungeon.player instanceof SlimeboundCharacter && !AbstractDungeon.player.orbs.isEmpty()) {
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new PotencyPower(AbstractDungeon.player, __instance, -1), -1));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new StrengthPower(AbstractDungeon.player, -1), -1));
                }
            }

            if (AbstractDungeon.player instanceof TheHexaghost) {
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new EnhancePower(-1)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new StrengthPower(AbstractDungeon.player, -1), -1));
                }
            }

            if (AbstractDungeon.player instanceof TheSnecko || AbstractDungeon.player instanceof GuardianCharacter) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, __instance, new StrengthPower(AbstractDungeon.player, -1)));
            }

            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }

        return SpireReturn.Continue();
    }
}