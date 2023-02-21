package hermit.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import hermit.cards.CursedWeapon;

public class getCopyPatch {
    @SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})

    public static class CursedWeaponOnInit {

        public static AbstractCard Postfix(AbstractCard __result) {
            if (__result.cardID.equals(CursedWeapon.ID)) {
                __result.baseDamage = __result.misc;
                __result.initializeDescription();
            }

            return __result;
        }
}
}
