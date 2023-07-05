package collector.patches;

import collector.actions.GainReservesAction;
import collector.util.NewReserves;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

import static collector.util.Wiz.att;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "useCard"
)
public class ReservesWithXCosts {
    public ReservesWithXCosts() {
    }

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.cost == -1) {
            int effect = c.energyOnUse;
            int count = NewReserves.reserveCount();
            if (count > 0) {
                effect += count;
                if (!c.freeToPlayOnce)
                    att(new GainReservesAction(-count));
            }
            c.energyOnUse = effect;
        }
    }

    public static class Locator extends SpireInsertLocator {
        public Locator() {
        }

        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractCard.class, "use");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
