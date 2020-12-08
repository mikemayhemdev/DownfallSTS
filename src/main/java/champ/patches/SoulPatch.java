package champ.patches;

import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;

public class SoulPatch {
    @SpirePatch(clz = Soul.class, method = "empower")
    public static class SoulNotBeBadPatch {
        @SpirePostfixPatch
        public static void patch(Soul __instance, AbstractCard card) {
            if (card instanceof AbstractBossCard) {
                ReflectionHacks.setPrivate(__instance, Soul.class, "target", new Vector2(AbstractCharBoss.boss.hb.cX, AbstractCharBoss.boss.hb.cY));
            }
        }
    }
}