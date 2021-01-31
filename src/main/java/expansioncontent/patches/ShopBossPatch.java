package expansioncontent.patches;

import automaton.AutomatonChar;
import basemod.ReflectionHacks;
import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.shop.ShopScreen;
import downfall.patches.EvilModeCharacterSelect;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.cards.*;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import theHexaghost.TheHexaghost;

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

    public static AbstractCard getReplacement(AbstractCard.CardRarity rarity) {
        ArrayList<AbstractCard> potentialCardsList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.color == CardColorEnumPatch.CardColorPatch.BOSS && q.rarity == rarity && okayToSpawn(q)) {
                potentialCardsList.add(q);
            }
        }
        return potentialCardsList.get(AbstractDungeon.merchantRng.random(0, potentialCardsList.size() - 1));
    }

    public static boolean okayToSpawn(AbstractCard q) {
        if (q.rarity == AbstractCard.CardRarity.SPECIAL) return false;

        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            if (q.cardID.equals(PrepareCrush.ID)) {
                return false;
            }
        }
        if (AbstractDungeon.player instanceof TheHexaghost || RandomCardWithTagAction.hexaLocked()) {
           if (q.cardID.equals(Hexaburn.ID)) return false;
        }
        if (AbstractDungeon.player instanceof GuardianCharacter || RandomCardWithTagAction.guardianLocked()) {
            if (q.cardID.equals(GuardianWhirl.ID)) return false;
        }
        if (AbstractDungeon.player instanceof ChampChar || RandomCardWithTagAction.champLocked()) {
            if (q.cardID.equals(LastStand.ID)) return false;
        }
        if (AbstractDungeon.player instanceof AutomatonChar || RandomCardWithTagAction.autoLocked()) {
            return !q.cardID.equals(HyperBeam.ID);
        }
        return true;
    }
}