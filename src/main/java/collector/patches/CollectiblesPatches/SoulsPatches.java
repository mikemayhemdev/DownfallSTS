package collector.patches.CollectiblesPatches;


import basemod.ReflectionHacks;
import collector.CollectorMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;

public class SoulsPatches {

    public static boolean REDIRECT_SOULS_TO_COLLECTION = false;

    @SpirePatch(
            clz = Soul.class,
            method = "onToDeck",
            paramtypez = {
                    AbstractCard.class,
                    boolean.class,
                    boolean.class
            }
    )
    public static class SoulsToCollectedPile {
        public static void Postfix(Soul __instance, AbstractCard card, boolean randomSpot, boolean visualOnly) {
            if (REDIRECT_SOULS_TO_COLLECTION) {
                ReflectionHacks.setPrivate(__instance, Soul.class, "target", CollectorMod.combatCollectionPileButton.getLocation());
            }
        }
    }

}