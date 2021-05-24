package collector.patches.TorchHeadPatches;

import basemod.ReflectionHacks;
import collector.CollectorChar;
import collector.TorchChar;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import javassist.CtBehavior;

public class MonsterTargetPatch { public static AbstractPlayer prevPlayer = null;

        public static AbstractCreature redirectTarget = null;

@SpirePatch(clz = GameActionManager.class, method = "getNextAction")
public static class ChangeTargetPatch {
    @SpireInsertPatch(locator = BeforeTakeTurnLocator.class)
    public static void Insert(GameActionManager __instance, AbstractMonster ___m) {
        AbstractCreature target = CollectorChar.getCurrentTarget(___m);


        if (target instanceof AbstractMonster) {
            redirectTarget = target;
        } else if (AbstractDungeon.player instanceof CollectorChar && target instanceof TorchChar) {
            if (prevPlayer != null) {
                System.out.println("BUG - MonsterTargetPatch!");
            }
            prevPlayer = AbstractDungeon.player;
            AbstractDungeon.player = ((CollectorChar) AbstractDungeon.player).torch;
        }
    }

    @SpirePostfixPatch
    public static void Postfix(GameActionManager __instance) {
        if (prevPlayer != null) {
            AbstractDungeon.player = prevPlayer;
            prevPlayer = null;
        }
        redirectTarget = null;
    }
}

private static class BeforeTakeTurnLocator extends SpireInsertLocator {
    @Override
    public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "takeTurn");
        return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
    }
}

@SpirePatch(clz = GameActionManager.class, method = "addToBottom")
@SpirePatch(clz = GameActionManager.class, method = "addToTop")
public static class ForceRedirectPatch {
    @SpirePrefixPatch
    public static void Prefix(GameActionManager __instance, @ByRef AbstractGameAction[] action) {
        if (redirectTarget != null) {
            if (action[0].target == AbstractDungeon.player) {
                action[0].target = redirectTarget;

                if (action[0] instanceof ApplyPowerAction) {
                    AbstractPower p = ReflectionHacks.getPrivate(action[0], ApplyPowerAction.class, "powerToApply");
                    int amount = p.amount > 0 ? p.amount : 1;
                    if (p instanceof WeakPower) {
                        action[0] = new ApplyPowerAction(redirectTarget, action[0].source, new WeakPower(redirectTarget, amount, true), amount);
                    } else if (p instanceof PoisonPower) {
                        action[0] = new ApplyPowerAction(redirectTarget, action[0].source, new PoisonPower(redirectTarget, action[0].source, amount), amount);
                    } else {
                        action[0] = new ApplyPowerAction(redirectTarget, action[0].source, new VulnerablePower(redirectTarget, amount, true), amount);
                    }
                }
            }
        }
    }
}
}