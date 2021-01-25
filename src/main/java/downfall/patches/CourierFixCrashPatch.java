package downfall.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import expansioncontent.patches.CardColorEnumPatch;
import expansioncontent.patches.ShopBossPatch;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.megacrit.cardcrawl.shop.ShopScreen.getBuyMsg;

@SpirePatch(clz = ShopScreen.class, method = "purchaseCard")
public class CourierFixCrashPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c"}
    )
    public static SpireReturn replaceCrashWithGood(ShopScreen __instance, AbstractCard hoveredCard, AbstractCard c) {
        if (hoveredCard.color == CardColorEnumPatch.CardColorPatch.BOSS) {
            c = ShopBossPatch.getReplacement(hoveredCard.rarity);
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onPreviewObtainCard(c);
            c.current_x = hoveredCard.current_x;
            c.current_y = hoveredCard.current_y;
            c.target_x = c.current_x;
            c.target_y = c.current_y;
            try {
                Method m = ShopScreen.class.getDeclaredMethod("setPrice", AbstractCard.class);
                m.setAccessible(true);
                m.invoke(__instance, c);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            __instance.colorlessCards.set(__instance.colorlessCards.indexOf(hoveredCard), c);
            ReflectionHacks.setPrivate(__instance, ShopScreen.class, "hoveredCard", null);
            InputHelper.justClickedLeft = false;
            ReflectionHacks.setPrivate(__instance, ShopScreen.class, "notHoveredTimer", 1.0F);
            ReflectionHacks.setPrivate(__instance, ShopScreen.class, "speechTimer", MathUtils.random(40.0F, 60.0F));
            __instance.playBuySfx();
            __instance.createSpeech(getBuyMsg());
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
        }
    }
}
