package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.DamageNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import slimebound.SlimeboundMod;


public class StrikeEffectPatch {
    @SpirePatch(clz = StrikeEffect.class, method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractCreature.class,
                    float.class,
                    float.class,
                    int.class})
    public static class removeStrikeVFX {
        public static SpireReturn<Void> Prefix(StrikeEffect obj, AbstractCreature target, float x, float y, int number) {
            ////SlimeboundMod.logger.info("Patch hit.");
            if (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND && SlimeboundMod.disabledStrikeVFX && AbstractDungeon.player == target) {

                ////SlimeboundMod.logger.info("Patch hit player is Slimebound.");
                AbstractDungeon.effectsQueue.add(new DamageNumberEffect(target, x, y, number));
                SlimeboundMod.disabledStrikeVFX = false;
                return SpireReturn.Return(null);
            } else {

                return SpireReturn.Continue();

            }

        }
    }
}


