
package slimebound.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import slimebound.SlimeboundMod;

import java.util.ArrayList;



public class StrikeEffectPatch {
    @SpirePatch(clz = StrikeEffect.class, method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    AbstractCreature.class,
                    float.class,
                    float.class,
                    int.class})
    public static class removeStrikeVFX {
        public static SpireReturn<Void> Prefix(StrikeEffect obj, AbstractCreature target, float x, float y, int number) {
            //SlimeboundMod.logger.info("Patch hit.");
            if (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND && SlimeboundMod.disabledStrikeVFX && AbstractDungeon.player == target) {

                //SlimeboundMod.logger.info("Patch hit player is Slimebound.");
                AbstractDungeon.effectsQueue.add(new DamageNumberEffect(target, x, y, number));
                SlimeboundMod.disabledStrikeVFX = false;
                return SpireReturn.Return(null);
            } else {

                return SpireReturn.Continue();

            }

        }
    }
}


