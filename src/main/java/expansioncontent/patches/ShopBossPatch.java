package expansioncontent.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.cards.GuardianWhirl;
import expansioncontent.cards.Hexaburn;
import expansioncontent.cards.HyperBeam;
import expansioncontent.cards.LastStand;

import java.util.ArrayList;

@SpirePatch(
        clz = ShopScreen.class,
        method = "initCards"
)
public class ShopBossPatch {
    public static void Postfix(ShopScreen __instance) {
        if (EvilModeCharacterSelect.evilMode) {
            ArrayList<AbstractCard> colorlessCards = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "colorlessCards");
            if (AbstractDungeon.merchantRng.randomBoolean()) {
                int x = colorlessCards.get(0).price;
                colorlessCards.set(0, getReplacement(colorlessCards.get(0).rarity));
                colorlessCards.get(0).price = x;
                for (final AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(colorlessCards.get(0));
                }
            }
            if (AbstractDungeon.merchantRng.randomBoolean()) {
                int x = colorlessCards.get(1).price;
                colorlessCards.set(1, getReplacement(colorlessCards.get(1).rarity));
                colorlessCards.get(1).price = x;
                for (final AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(colorlessCards.get(1));
                }
            }
        }
    }

    public static AbstractCard getReplacement(AbstractCard.CardRarity rarity) {
        ArrayList<AbstractCard> potentialCardsList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.color == CardColorEnumPatch.CardColorPatch.BOSS && q.rarity == rarity && okayToSpawn(q)) {
                potentialCardsList.add(q.makeCopy());
            }
        }
        return potentialCardsList.get(AbstractDungeon.merchantRng.random(0, potentialCardsList.size() - 1));
    }

    public static boolean okayToSpawn(AbstractCard q) {
        if (q.rarity == AbstractCard.CardRarity.SPECIAL) return false;

        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.SLIMEBOUND)) {
            if (q.cardID.equals(downfallMod.expansioncontentModID + ":PrepareCrush")) {
                return false;
            }
        }
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.THE_SPIRIT) || RandomCardWithTagAction.hexaLocked()) {
            if (q.cardID.equals(Hexaburn.ID)) return false;
        }
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GUARDIAN) || RandomCardWithTagAction.guardianLocked()) {
            if (q.cardID.equals(GuardianWhirl.ID)) return false;
        }
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.THE_CHAMP) || RandomCardWithTagAction.champLocked()) {
            if (q.cardID.equals(LastStand.ID)) return false;
        }
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.THE_AUTOMATON) || RandomCardWithTagAction.autoLocked()) {
            return !q.cardID.equals(HyperBeam.ID);
        }
        return true;
    }
}