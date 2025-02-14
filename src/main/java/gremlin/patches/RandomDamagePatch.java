package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.relics.FragmentationGrenade;

@SpirePatch(
        clz= DamageRandomEnemyAction.class,
        method=SpirePatch.CONSTRUCTOR
)
public class RandomDamagePatch {
    public static void Postfix(DamageRandomEnemyAction __instance){
        if(AbstractDungeon.player.hasRelic(FragmentationGrenade.ID)){
            AbstractDungeon.player.getRelic(FragmentationGrenade.ID).flash();
            DamageInfo old = ReflectionHacks.getPrivate(__instance, DamageRandomEnemyAction.class, "info");
            ReflectionHacks.setPrivate(__instance, DamageRandomEnemyAction.class, "info",
                    new DamageInfo(old.owner, old.base + FragmentationGrenade.OOMPH, old.type));
        }
    }
}
