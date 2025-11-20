package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.relics.FragmentationGrenade;

@SpirePatch(
        clz= AttackDamageRandomEnemyAction.class,
        method=SpirePatch.CONSTRUCTOR,
        paramtypez={AttackDamageRandomEnemyAction.class}
)
public class AttackDamageRandomPatch {
    public static void Postfix(AttackDamageRandomEnemyAction __instance){
        if(AbstractDungeon.player.hasRelic(FragmentationGrenade.ID)){
            AbstractDungeon.player.getRelic(FragmentationGrenade.ID).flash();
            DamageInfo old = ReflectionHacks.getPrivate(__instance, AttackDamageRandomEnemyAction.class, "info");
            ReflectionHacks.setPrivate(__instance, AttackDamageRandomEnemyAction.class, "info",
                    new DamageInfo(old.owner, old.base + FragmentationGrenade.OOMPH, old.type));
        }
    }
}