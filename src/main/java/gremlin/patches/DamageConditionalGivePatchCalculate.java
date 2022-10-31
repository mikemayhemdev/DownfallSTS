package gremlin.patches;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import gremlin.powers.DamageConditionalGivePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
public class DamageConditionalGivePatchCalculate {
    @SpireInsertPatch(
            rloc=23,
            localvars={"player", "tmp"}
    )
    public static SpireReturn Insert(AbstractCard abstractCard, AbstractMonster mo,
                                     AbstractPlayer player, @ByRef float[] tmp) {
        for(AbstractPower p : player.powers) {
            if (p instanceof DamageConditionalGivePower) {
                float tmpcpy = tmp[0];
                tmp[0] = ((DamageConditionalGivePower) p).atDamageConditionalGive(tmpcpy, abstractCard,
                        mo, abstractCard.damageTypeForTurn);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=34,
            localvars={"player", "tmp"}
    )
    public static SpireReturn FinalInsert(AbstractCard abstractCard, AbstractMonster mo,
                                          AbstractPlayer player, @ByRef float[] tmp) {
        for(AbstractPower p : player.powers) {
            if (p instanceof DamageConditionalGivePower) {
                float tmpcpy = tmp[0];
                tmp[0] = ((DamageConditionalGivePower) p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                        mo, abstractCard.damageTypeForTurn);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rlocs=81,
            localvars={"player", "tmp", "i"}
    )
    public static SpireReturn MultiInsert(AbstractCard abstractCard, AbstractMonster mo,
                                          AbstractPlayer player, float[] tmp, int i) {
        for(AbstractPower p : player.powers) {
            if (p instanceof DamageConditionalGivePower) {
                float tmpcpy = tmp[i];
                tmp[i] = ((DamageConditionalGivePower) p).atDamageConditionalGive(tmpcpy, abstractCard,
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(i), abstractCard.damageTypeForTurn);
            }
        }
        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            rloc=104,
            localvars={"player", "tmp"}
    )
    public static SpireReturn MultiFinalInsert(AbstractCard abstractCard, AbstractMonster mo,
                                               AbstractPlayer player, float[] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            for (AbstractPower p : player.powers) {
                if (p instanceof DamageConditionalGivePower) {
                    float tmpcpy = tmp[i];
                    tmp[i] = ((DamageConditionalGivePower) p).atFinalDamageConditionalGive(tmpcpy, abstractCard,
                            AbstractDungeon.getCurrRoom().monsters.monsters.get(i), abstractCard.damageTypeForTurn);
                }
            }
        }
        return SpireReturn.Continue();
    }
}

