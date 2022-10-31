package automaton.patches;

import automaton.cards.ChosenStrike;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import javassist.CtBehavior;

public class LoadMiscPatch {
  @SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
  public static class MiscPatch {
    @SpireInsertPatch(locator = Locator.class, localvars = {"retVal"})
    public static void LoadMisc(String key, int upgradeTime, int misc, AbstractCard retVal) {
      if (retVal.cardID.equals(ChosenStrike.ID))
        if (misc != 0) {
          retVal.baseDamage += misc;
          retVal.damage += misc;
          retVal.initializeDescription();
        }
    }
    
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
        Matcher.FieldAccessMatcher fieldAccessMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "misc");
        return LineFinder.findInOrder(ctMethodToPatch, (Matcher)fieldAccessMatcher);
      }
    }
  }
}