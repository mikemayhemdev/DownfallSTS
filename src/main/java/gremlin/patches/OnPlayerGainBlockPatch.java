package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.powers.OnPlayerGainBlockPower;

@SpirePatch(
        clz= AbstractCreature.class,
        method="addBlock"
)
public class OnPlayerGainBlockPatch {
    @SpireInsertPatch(
            rloc=20,
            localvars={"tmp"}
    )
    public static void Insert(AbstractCreature __instance, int blockAmount, @ByRef float[] tmp) {
        if(__instance.isPlayer){
            for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (final AbstractPower p2 : m.powers) {
                    if(p2 instanceof OnPlayerGainBlockPower)
                    {
                        tmp[0] = ((OnPlayerGainBlockPower) p2).onPlayerGainBlock(tmp[0]);
                    }
                }
            }
        }
    }
}
