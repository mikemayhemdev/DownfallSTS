package charbosses.patches;

import charbosses.bosses.AbstractCharBoss;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

import static com.megacrit.cardcrawl.powers.WeakPower.DESCRIPTIONS;

public class PaperKraneWeakPatch {
    @SpirePatch(
            clz = WeakPower.class,
            method = "atDamageGive"
    )
    public static class SuperWeakEffect {
        @SpirePrefixPatch
        public static SpireReturn<Float> Prefix(WeakPower instance, float damage, DamageType type) {
            if (type == DamageInfo.DamageType.NORMAL && instance.owner.isPlayer &&
                    AbstractCharBoss.boss != null && AbstractCharBoss.boss.hasRelic("Paper Crane")) {
                return SpireReturn.Return(damage * 0.6F);
            }
            else
                return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = WeakPower.class,
            method = "updateDescription"
    )
    public static class SuperWeakText {
        @SpirePostfixPatch
        public static void Postfix(WeakPower instance) {
            if (instance.owner != null && instance.owner.isPlayer &&
                    AbstractCharBoss.boss != null && AbstractCharBoss.boss.hasRelic("Paper Crane")) {
                if (instance.amount == 1)
                    instance.description = DESCRIPTIONS[0] + 40 + DESCRIPTIONS[1] + instance.amount + DESCRIPTIONS[2];
                else
                    instance.description = DESCRIPTIONS[0] + 40 + DESCRIPTIONS[1] + instance.amount + DESCRIPTIONS[3];
            }
        }
    }
}
