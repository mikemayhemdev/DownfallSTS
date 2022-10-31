package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import hermit.cards.GoldenBullet;
import javassist.CtBehavior;

public class getMiscPatch {
    @SpirePatch(
            clz = CardLibrary.class,
            method = "getCopy",
            paramtypez = { String.class, int.class, int.class }
    )
    public static class MiscPatch
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "retVal" }
        )
        public static void LoadMisc(String key, int upgradeTime, int misc, AbstractCard retVal)
        {
            if (retVal.cardID.equals(GoldenBullet.ID))
            {
                if (misc != 0)
                {
                    retVal.updateCost(-misc);
                    retVal.initializeDescription();
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "misc");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}