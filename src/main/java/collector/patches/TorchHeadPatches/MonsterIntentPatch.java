package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MonsterIntentPatch {
    public static AbstractPlayer prevPlayer = null;

    @SpirePatch(clz = AbstractMonster.class, method = "applyPowers")
    @SpirePatch(clz = AbstractMonster.class, method = "createIntent")
    public static class ChangeTargetPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractMonster __instance) {
            if (AbstractDungeon.player instanceof CollectorChar && CollectorChar.getCurrentTarget(__instance) instanceof TorchChar) {
                if (prevPlayer != null) {
                    System.out.println("BUG - MonsterIntentPatch!");
                }
                prevPlayer = AbstractDungeon.player;
                AbstractDungeon.player = ((CollectorChar) AbstractDungeon.player).torch;
            }
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractMonster __instance) {
            if (prevPlayer != null) {
                AbstractDungeon.player = prevPlayer;
                prevPlayer = null;
            }
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "calculateDamage")
    public static class TeamKillPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster __instance, int dmg, @ByRef int[] ___intentDmg) {
            AbstractCreature target = CollectorChar.getCurrentTarget(__instance);
            if (target instanceof AbstractMonster) {
                float tmp = dmg;
                if (Settings.isEndless && AbstractDungeon.player.hasBlight("DeadlyEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("DeadlyEnemies").effectFloat();
                    tmp *= mod;
                }

                for (AbstractPower p : __instance.powers) {
                    tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
                }

                for (AbstractPower p : target.powers) {
                    tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
                }

                for (AbstractPower p : __instance.powers) {
                    tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
                }

                for (AbstractPower p : target.powers) {
                    tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL);
                }

                dmg = MathUtils.floor(tmp);
                if (dmg < 0) {
                    dmg = 0;
                }

                ___intentDmg[0] = dmg;
            }
        }
    }
}