package downfall.patches;

import automaton.FunctionHelper;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.orbs.StasisOrb;


public class ApotheosisStasisFunctionPatch {
    @SpirePatch(
            clz = ApotheosisAction.class,
            method = "update"
    )
    public static class TimeToLearn {
        @SpirePrefixPatch
        public static void Prefix(ApotheosisAction instance) {
            float duration = ReflectionHacks.getPrivate(instance, AbstractGameAction.class, "duration");
            if (duration == Settings.ACTION_DUR_MED) {
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (o instanceof StasisOrb) {
                        ((StasisOrb) o).stasisCard.upgrade();
                    }
                }
                if (FunctionHelper.doStuff) {
                    for (AbstractCard q : FunctionHelper.held.group) {
                        q.upgrade();
                    }
                    FunctionHelper.genPreview();
                }
            }
        }
    }
}
