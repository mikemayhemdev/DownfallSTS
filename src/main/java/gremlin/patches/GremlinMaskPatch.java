package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.GremlinMask;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= GremlinMask.class,
        method="atBattleStart"
)
public class GremlinMaskPatch {
    public static SpireReturn Prefix(GremlinMask __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            __instance.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, __instance));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new StrengthPower(AbstractDungeon.player, 1), 1));
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
