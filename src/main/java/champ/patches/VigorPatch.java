package champ.patches;

import basemod.ReflectionHacks;
import champ.cards.CrookedStrike;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class VigorPatch {
    @SpirePatch(clz = VigorPower.class, method = "onUseCard")
    public static class DontConsumeVigotPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> patch(VigorPower __instance, AbstractCard card, UseCardAction action) {
            if (card instanceof CrookedStrike){

                __instance.flash();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}