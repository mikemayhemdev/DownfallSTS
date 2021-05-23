package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import gremlin.powers.CrippledPower;

@SpirePatch(
        clz= AbstractImageEvent.class,
        method=SpirePatch.CLASS
)
public class GremlinEventFixPatch1 {
    public static SpireField<Boolean> ranExtraCheck = new SpireField<>(() -> false);

    // The strings to detect that indicate this is an HP-loss event.
    private static String[] REPLACEMENTSTRINGS = CardCrawlGame.languagePack.getUIString("Gremlin:EventReplacementStrings").TEXT;

    // Check if an event option involves the player losing HP via their text.
    protected static boolean needs_replacement(String msg) {
        return msg.contains(REPLACEMENTSTRINGS[0]) && msg.contains(REPLACEMENTSTRINGS[1]);
    }

    @SpirePatch(
            clz= AbstractImageEvent.class,
            method="update"
    )
    public static class RunCheck {
        public static void Postfix(AbstractImageEvent __instance){
            if (!ranExtraCheck.get(__instance)) {
                GremlinEventFixPatch2.insideDamage = false;
                for (LargeDialogOptionButton b : __instance.imageEventText.optionList) {
                    if (needs_replacement(b.msg)) {
                        String numbertext = b.msg.split(REPLACEMENTSTRINGS[0])[1].split(REPLACEMENTSTRINGS[1])[0];
                        int number = Integer.parseInt(numbertext);
                        b.msg = b.msg.replace(REPLACEMENTSTRINGS[0], REPLACEMENTSTRINGS[2]);
                        b.msg = b.msg.replace(numbertext, String.valueOf((number + 4) / 5));
                    }
                }
                ranExtraCheck.set(__instance, true);
            }
        }
    }
}
