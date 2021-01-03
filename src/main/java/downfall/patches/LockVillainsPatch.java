//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.unlock.UnlockTracker",
        method = "refresh"
)
public class LockVillainsPatch {
    public LockVillainsPatch() {
    }

    public static void Postfix() {
        UnlockTracker.addCharacter("Guardian");
        UnlockTracker.addCharacter("Hexaghost");
        UnlockTracker.addCharacter("Champ");
        UnlockTracker.addCharacter("Automaton");
        UnlockTracker.addCharacter("Snecko");
    }
}
