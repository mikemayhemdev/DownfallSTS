package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import gremlin.patches.GremlinEnum;

@SpirePatch(
        clz = AbstractImageEvent.class,
        method = SpirePatch.CLASS
)
public class GremlinEventFixPatch1 {
    public static SpireField<Boolean> ranExtraCheck = new SpireField<>(() -> false);

    // The strings to detect that indicate this is an HP-loss event.
    private static String[] REPLACEMENTSTRINGS = CardCrawlGame.languagePack.getUIString("Gremlin:EventReplacementStrings").TEXT;

    // Check if an event option involves the player losing HP via their text.
    protected static boolean needs_replacement(String msg) {
        return AbstractDungeon.player.chosenClass == GremlinEnum.GREMLIN && ((msg.contains(REPLACEMENTSTRINGS[0]) && msg.contains(REPLACEMENTSTRINGS[1])) || (msg.contains(REPLACEMENTSTRINGS[3]) && msg.contains(REPLACEMENTSTRINGS[4])));
    }

    @SpirePatch(
            clz = AbstractImageEvent.class,
            method = "update"
    )
    public static class RunCheck {
        public static void Postfix(AbstractImageEvent __instance) {
            if (!ranExtraCheck.get(__instance)) {
                GremlinEventFixPatch2.insideDamage = false;
                for (LargeDialogOptionButton b : __instance.imageEventText.optionList) {
                    if (needs_replacement(b.msg)) {
                        if (b.msg.contains(REPLACEMENTSTRINGS[0]) && b.msg.contains(REPLACEMENTSTRINGS[1])) {
                            String numbertext = b.msg.split(REPLACEMENTSTRINGS[0])[1].split(REPLACEMENTSTRINGS[1])[0];
                            String actualnumber = numbertext.replaceAll("\\D+", "");
                            try {
                                int number = Integer.parseInt(actualnumber);
                                b.msg = b.msg.replace(REPLACEMENTSTRINGS[0], REPLACEMENTSTRINGS[2]);
                                b.msg = b.msg.replace(numbertext, numbertext.replace(actualnumber, String.valueOf((number + 4) / 5)));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                        else if (b.msg.contains(REPLACEMENTSTRINGS[3]) && b.msg.contains(REPLACEMENTSTRINGS[4])) {
                            String numbertext = b.msg.split(REPLACEMENTSTRINGS[3])[1].split(REPLACEMENTSTRINGS[4])[0];
                            String actualnumber = numbertext.replaceAll("\\D+", "");
                            try {
                                int number = Integer.parseInt(actualnumber);
                                b.msg = b.msg.replace(REPLACEMENTSTRINGS[3], REPLACEMENTSTRINGS[5]);
                                b.msg = b.msg.replace(numbertext, numbertext.replace(actualnumber, String.valueOf((number + 4) / 5)));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                }
                ranExtraCheck.set(__instance, true);
            }
        }
    }
}
