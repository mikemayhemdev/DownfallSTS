package charbosses.patches;


import basemod.ReflectionHacks;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

@SpirePatch(clz = ApplyPowerAction.class, method = "update")

public class ApplyPowerPatch {

    @SpirePrefixPatch
    public static SpireReturn Prefix(ApplyPowerAction instance) {

        float duration = (float) ReflectionHacks.getPrivate(instance, AbstractGameAction.class, "duration");
        float startingDuration = (float) ReflectionHacks.getPrivate(instance, ApplyPowerAction.class, "startingDuration");
        AbstractPower powerToApply = (AbstractPower) ReflectionHacks.getPrivate(instance, ApplyPowerAction.class, "powerToApply");

        if (instance.target != null && !instance.target.isDeadOrEscaped()) {
            if (duration == startingDuration) {
                if (powerToApply instanceof NoDrawPower && instance.target.hasPower(powerToApply.ID)) {
                    instance.isDone = true;
                    return SpireReturn.Return(null);
                }
                if (instance.target instanceof AbstractMonster && instance.target.isDeadOrEscaped()) {
                    ReflectionHacks.setPrivate(instance, AbstractGameAction.class, "duration", 0F);
                    instance.isDone = true;
                    return SpireReturn.Return(null);
                }

                if (instance.target instanceof AbstractCharBoss) {

                    AbstractCharBoss cB = (AbstractCharBoss) instance.target;

                    //Power Checks as needed for relics like Ginger
                    if ((cB.hasRelic("Ginger")) && (powerToApply.ID.equals("Weakened"))) {
                        cB.getRelic("Ginger").flash();
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(instance.target, instance.TEXT[1]));
                        float newDuration = duration -= Gdx.graphics.getDeltaTime();
                        ReflectionHacks.setPrivate(instance, AbstractGameAction.class, "duration", newDuration);
                        return SpireReturn.Return(null);
                    }


                }
            }

        }
            return SpireReturn.Continue();

    }
}

