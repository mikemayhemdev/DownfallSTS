//package champ.patches;
//
//import basemod.ReflectionHacks;
//import champ.powers.CounterPower;
//import champ.relics.PowerArmor;
//import com.badlogic.gdx.Gdx;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
//import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
//import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
//import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.StrengthPower;
//import com.megacrit.cardcrawl.powers.watcher.VigorPower;
//import gremlin.powers.UnforgivingPower;
//
//import java.lang.reflect.Field;
//
//import static hermit.util.Wiz.att;
//import static hermit.util.Wiz.pwrAmt;
//
//@SpirePatch(
//        clz= ApplyPowerAction.class,
//        method="update"
//)
//public class VigorCounterPowerArmorPatch {
//    @SpireInsertPatch(
//            rloc=35
//    )
//    public static SpireReturn Insert(ApplyPowerAction __instance) {
//        AbstractPower powerToApply = (AbstractPower) ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
//        if ((powerToApply instanceof VigorPower || powerToApply instanceof CounterPower) &&
//                powerToApply.amount > 0 &&
//                AbstractDungeon.player.hasRelic(PowerArmor.ID)) {
//            if (pwrAmt(AbstractDungeon.player, powerToApply.ID) >= PowerArmor.CAP_RESOLVE_ETC) {
//
//                ((PowerArmor)(AbstractDungeon.player.getRelic(PowerArmor.ID))).onTrigger((powerToApply.amount));
//
//                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(__instance.target, ApplyPowerAction.TEXT[1]));
//                float duration = (float) getPrivateInherited(__instance, ApplyPowerAction.class, "duration");
//                duration -= Gdx.graphics.getDeltaTime();
//                ReflectionHacks.setPrivateInherited(__instance, ApplyPowerAction.class, "duration", duration);
//                return SpireReturn.Return(null);
//            }
//        }
//        return SpireReturn.Continue();
//    }
//
//    public static Object getPrivateInherited(Object obj, Class<?> objClass, String fieldName) {
//        try {
//            Field targetField = objClass.getSuperclass().getDeclaredField(fieldName);
//            targetField.setAccessible(true);
//            return targetField.get(obj);
//        } catch (Exception e) {
//        }
//
//        return null;
//    }
//}