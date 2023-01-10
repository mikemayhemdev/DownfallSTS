package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.FearNoEvilAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class FearNoEvilForHeroPatch {

    static AbstractMonster boss;

    @SpirePatch(
            clz = FearNoEvilAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class GetMonsterVariable {
        public static void Prefix(FearNoEvilAction __instance, AbstractMonster m, DamageInfo info) {
            boss = m;
        }
    }

    @SpirePatch(
            clz = FearNoEvilAction.class,
            method = "update"
    )
    public static class ApplyBossIntentCheck {
        public static void Prefix(FearNoEvilAction __instance) {
            if (boss != null && boss.getIntentBaseDmg() >= 0) {
                AbstractDungeon.actionManager.addToTop(new ChangeStanceAction("Calm"));
            }
        }
    }
}