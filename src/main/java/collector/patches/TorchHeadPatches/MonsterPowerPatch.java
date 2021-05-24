package collector.patches.TorchHeadPatches;

import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class MonsterPowerPatch {
    public static AbstractPlayer prevPlayer = null;

    static void changePlayer(AbstractPlayer player) {
        if (player != null) {
            if (prevPlayer != null) {
                System.out.println("BUG - MonsterPowerPatch!");
            }
            prevPlayer = AbstractDungeon.player;
            AbstractDungeon.player = player;
        }
    }

    static void RestorePlayer() {
        if (prevPlayer != null) {
            AbstractDungeon.player = prevPlayer;
            prevPlayer = null;
        }
    }

    @SpirePatch(clz = BlockReturnPower.class, method = "onAttacked")
    public static class TalkToTheHandPatch {
        @SpirePrefixPatch
        public static void Prefix(BlockReturnPower __instance, DamageInfo info, int damageAmount) {
            if (info.owner instanceof TorchChar) {
                changePlayer((AbstractPlayer) info.owner);
            }
        }

        @SpirePostfixPatch
        public static void Postfix(BlockReturnPower __instance, DamageInfo info, int damageAmount) {
            RestorePlayer();
        }
    }

    @SpirePatch(clz = SporeCloudPower.class, method = "onDeath")
    public static class SporeCloudPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(SporeCloudPower __instance) {
            AbstractCreature c = CollectorChar.getCurrentTarget(__instance.owner);
            if (c instanceof AbstractMonster) {
                if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
                    CardCrawlGame.sound.play("SPORE_CLOUD_RELEASE");
                    __instance.flashWithoutSound();
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, null, new VulnerablePower(c, __instance.amount, true), __instance.amount));
                }
                return SpireReturn.Return(null);
            } else if (c instanceof TorchChar) {
                changePlayer((TorchChar) c);
            }
            return SpireReturn.Continue();
        }

        @SpirePostfixPatch
        public static void Postfix(SporeCloudPower __instance) {
            RestorePlayer();
        }
    }

    @SpirePatch(clz = SharpHidePower.class, method = "onUseCard")
    public static class SharpHidePatch {
        @SpirePrefixPatch
        public static void Prefix(SharpHidePower __instance, AbstractCard card, UseCardAction action) {
            if (CollectorChar.getCurrentTarget(__instance.owner) instanceof TorchChar) {
                changePlayer(CollectorChar.getDragon());
            }
        }

        @SpirePostfixPatch
        public static void Postfix(SharpHidePower __instance, AbstractCard card, UseCardAction action) {
            RestorePlayer();
        }
    }

    @SpirePatch(clz = BeatOfDeathPower.class, method = "onAfterUseCard")
    public static class BeatOfDeathPatch {
        @SpirePrefixPatch
        public static void Prefix(BeatOfDeathPower __instance, AbstractCard card, UseCardAction action) {
            if (CollectorChar.getCurrentTarget(__instance.owner) instanceof TorchChar) {
                changePlayer(CollectorChar.getDragon());
            }
        }

        @SpirePostfixPatch
        public static void Postfix(BeatOfDeathPower __instance, AbstractCard card, UseCardAction action) {
            RestorePlayer();
        }
    }

    @SpirePatch(clz = ExplosivePower.class, method = "duringTurn")
    public static class ExplosivePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(ExplosivePower __instance) {
            AbstractCreature c = CollectorChar.getCurrentTarget(__instance.owner);
            if (c instanceof AbstractMonster) {
                if (__instance.amount == 1 && !__instance.owner.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(__instance.owner.hb.cX, __instance.owner.hb.cY), 0.1F));
                    AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster) __instance.owner));
                    DamageInfo damageInfo = new DamageInfo(__instance.owner, 30, DamageInfo.DamageType.THORNS);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(c, damageInfo, AbstractGameAction.AttackEffect.FIRE, true));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(__instance.owner, __instance.owner, ExplosivePower.POWER_ID, 1));
                    __instance.updateDescription();
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
