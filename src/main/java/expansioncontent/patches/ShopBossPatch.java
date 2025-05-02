package expansioncontent.patches;

import automaton.AutomatonChar;
import awakenedOne.AwakenedOneChar;
import basemod.ReflectionHacks;
import champ.ChampChar;
import collector.CollectorChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import downfall.patches.EvilModeCharacterSelect;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.cards.*;
import expansioncontent.expansionContentMod;
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
            if (1 == 1) {
                //  if (AbstractDungeon.merchantRng.randomBoolean()) {
                int x = colorlessCards.get(0).price;
                colorlessCards.set(0, getReplacement(colorlessCards.get(0).rarity));
                colorlessCards.get(0).price = x;
                for (final AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(colorlessCards.get(0));
                }
            }
            if (1 == 1) {
                //  if (AbstractDungeon.merchantRng.randomBoolean()) {
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

        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
            if (q.hasTag(expansionContentMod.STUDY_SLIMEBOSS)){
                return false;
            }
        }
        if (AbstractDungeon.player instanceof TheHexaghost || RandomCardWithTagAction.hexaLocked()) {
            if (q.hasTag(expansionContentMod.STUDY_HEXAGHOST)){
                return false;
            }
        }
        if (AbstractDungeon.player instanceof GuardianCharacter || RandomCardWithTagAction.guardianLocked()) {
            if (q.hasTag(expansionContentMod.STUDY_GUARDIAN)){
                return false;
            }
        }
        if (AbstractDungeon.player instanceof ChampChar || RandomCardWithTagAction.champLocked()) {
            if (q.hasTag(expansionContentMod.STUDY_CHAMP)){
                return false;
            }
        }
        if (AbstractDungeon.player instanceof AutomatonChar || RandomCardWithTagAction.autoLocked()) {
            if (q.hasTag(expansionContentMod.STUDY_AUTOMATON)){
                return false;
            }
        }
        if (AbstractDungeon.player instanceof CollectorChar || RandomCardWithTagAction.collectorLocked()) {
            if (q.hasTag(expansionContentMod.STUDY_COLLECTOR)){
                return false;
            }

        if (AbstractDungeon.player instanceof AwakenedOneChar || RandomCardWithTagAction.awakenedLocked()) {
            if (q.hasTag(expansionContentMod.STUDY_AWAKENEDONE)){
                return false;
            }
        }

        }

        return true;
    }
}
