package downfall.patches;

import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import guardian.stances.DefensiveMode;

import java.lang.reflect.Field;


public class StancePatch {

        @SpirePatch(
                clz = AbstractStance.class,
                method = "getStanceFromName"
        )
        public static class DefensiveMode_StancePatch{
            @SpirePrefixPatch
            public static SpireReturn<AbstractStance> returnStance(String name) {
                if (name.equals(DefensiveMode.STANCE_ID)) {
                    return SpireReturn.Return(new DefensiveMode());
                }
                if (name.equals(DefensiveStance.STANCE_ID)) {
                    return SpireReturn.Return(new DefensiveStance());
                }
                if (name.equals(BerserkerStance.STANCE_ID)) {
                    return SpireReturn.Return(new BerserkerStance());
                }
                if (name.equals(UltimateStance.STANCE_ID)) {
                    return SpireReturn.Return(new UltimateStance());
                }
                return SpireReturn.Continue();
            }
        }

        @SpirePatch(
                clz = StanceAuraEffect.class,
                method = SpirePatch.CONSTRUCTOR,
                paramtypez = {String.class}
        )
        public static class StanceAuraEffectPatch{
            @SpirePostfixPatch
            public static SpireReturn<Void> Postfix(StanceAuraEffect _instance,String stanceId) {
                if (stanceId.equals("DefensiveMode")) {
                    try{
                        Field colorField = _instance.getClass().getSuperclass().getDeclaredField("color");
                        colorField.setAccessible(true);
                        colorField.set(_instance, new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.5F, 0.55F), 0.0F));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return SpireReturn.Continue();
            }
        }





}