package expansioncontent.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.shop.ShopScreen;
import downfall.patches.EvilModeCharacterSelect;

import java.util.ArrayList;

@SpirePatch(
        clz = ShopScreen.class,
        method = "initCards"
)
public class ShopBossPatch {
    public static void Postfix(ShopScreen __instance) {
        if (EvilModeCharacterSelect.evilMode) {
            ArrayList<AbstractCard> colorlessCards = (ArrayList<AbstractCard>) ReflectionHacks.getPrivate(__instance, ShopScreen.class, "colorlessCards");
            if (AbstractDungeon.merchantRng.randomBoolean()) {
                int x = colorlessCards.get(0).price;
                colorlessCards.set(0, getReplacement(colorlessCards.get(0).rarity));
                colorlessCards.get(0).price = x;
            }
            if (AbstractDungeon.merchantRng.randomBoolean()) {
                int x = colorlessCards.get(1).price;
                colorlessCards.set(1, getReplacement(colorlessCards.get(1).rarity));
                colorlessCards.get(1).price = x;
            }
        }
    }

    private static AbstractCard getReplacement(AbstractCard.CardRarity rarity) {
        ArrayList<AbstractCard> potentialCardsList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.color == CardColorEnumPatch.CardColorPatch.BOSS && q.rarity == rarity) {
                potentialCardsList.add(q);
            }
        }
        return potentialCardsList.get(AbstractDungeon.merchantRng.random(0, potentialCardsList.size() - 1));
    }
}