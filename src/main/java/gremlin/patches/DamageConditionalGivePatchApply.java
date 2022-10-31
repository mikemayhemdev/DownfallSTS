package gremlin.patches;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import gremlin.powers.DamageConditionalGivePower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractCard.class, method = "applyPowers")
public class DamageConditionalGivePatchApply {
    @SpireInsertPatch(
            rloc=23,
            localvars={"player", "tmp"}
    )
    public static SpireReturn Insert(AbstractCard abstractCard, AbstractPlayer player, @ByRef float[] tmp) {
        for(AbstractPower p : player.powers) {
            if (p instanceof DamageConditionalGivePower) {
                float tmpcpy = tmp[0];
                tmp[0] = ((DamageConditionalGivePower) p).atDamageConditionalGive(tmpcpy, abstractCard,
                        null, abstractCard.damageTypeForTurn);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=34,
            localvars={"player", "tmp"}
    )
    public static SpireReturn FinalInsert(AbstractCard abstractCard, AbstractPlayer player, @ByRef float[] tmp) {
        for(AbstractPower p : player.powers) {
            if (p instanceof DamageConditionalGivePower) {
                float tmpcpy = tmp[0];
                tmp[0] = ((DamageConditionalGivePower) p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                        null, abstractCard.damageTypeForTurn);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rlocs=69,
            localvars={"player", "tmp", "i"}
    )
    public static SpireReturn MultiInsert(AbstractCard abstractCard, AbstractPlayer player, float[] tmp, int i) {
        for(AbstractPower p : player.powers) {
            if (p instanceof DamageConditionalGivePower) {
                float tmpcpy = tmp[i];
                tmp[i] = ((DamageConditionalGivePower) p).atDamageConditionalGive(tmpcpy, abstractCard,
                        null, abstractCard.damageTypeForTurn);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=83,
            localvars={"player", "tmp"}
    )
    public static SpireReturn MultiFinalInsert(AbstractCard abstractCard, AbstractPlayer player, float[] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            for (AbstractPower p : player.powers) {
                if (p instanceof DamageConditionalGivePower) {
                    float tmpcpy = tmp[i];
                    tmp[i] = ((DamageConditionalGivePower) p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                            null, abstractCard.damageTypeForTurn);
                }
            }
        }
        return SpireReturn.Continue();
    }
}

