package theTodo.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theTodo.cards.AbstractEasyCard;
import theTodo.util.CardArtRoller;
import theTodo.util.NonModButton;
import theTodo.util.PanelMinMaxSlider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static theTodo.TodoMod.colorCardsPrefs;

public class CardArtRollerPatches {

    public static ArrayList<String> stringAllCards(AbstractCard.CardType t) {
        ArrayList<String> cardsArrayList = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (BaseMod.isBaseGameCardColor(c.color) && c.type == t)
                cardsArrayList.add(c.cardID);
        }
        return cardsArrayList;
    }

    private static boolean didABigHeckinClickerino() {
        return UIAdditions.hueSlider.get(CardCrawlGame.cardPopup).bgHb.hovered || UIAdditions.contrastSlider.get(CardCrawlGame.cardPopup).bgHb.hovered || UIAdditions.lightnessSlider.get(CardCrawlGame.cardPopup).bgHb.hovered || UIAdditions.saturationSlider.get(CardCrawlGame.cardPopup).bgHb.hovered
                || UIAdditions.randomizeButton.get(CardCrawlGame.cardPopup).hb.hovered || UIAdditions.flipXButton.get(CardCrawlGame.cardPopup).hb.hovered || UIAdditions.nextButton.get(CardCrawlGame.cardPopup).hb.hovered || UIAdditions.prevButton.get(CardCrawlGame.cardPopup).hb.hovered || UIAdditions.saveButton.get(CardCrawlGame.cardPopup).hb.hovered;
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "close"
    )
    public static class DontDoThatLastThing {
        public static SpireReturn Prefix(SingleCardViewPopup __instance) {
            if (didABigHeckinClickerino())
                return SpireReturn.Return(null);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = SpirePatch.CLASS
    )
    public static class UIAdditions {
        public static SpireField<PanelMinMaxSlider> hueSlider = new SpireField<>(() -> new PanelMinMaxSlider("hue", 1500, 400, 0, 1, 0.5f, "", (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                CardArtRoller.infos.get(q.cardID).H = c.getValue();
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));
        public static SpireField<PanelMinMaxSlider> saturationSlider = new SpireField<>(() -> new PanelMinMaxSlider("saturation", 1500, 300, 0, 1, 0.5f, "", (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                CardArtRoller.infos.get(q.cardID).S = c.getValue();
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));
        public static SpireField<PanelMinMaxSlider> lightnessSlider = new SpireField<>(() -> new PanelMinMaxSlider("lightness", 1500, 200, 0, 1, 0.5f, "", (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                CardArtRoller.infos.get(q.cardID).L = c.getValue();
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));
        public static SpireField<PanelMinMaxSlider> contrastSlider = new SpireField<>(() -> new PanelMinMaxSlider("contrast", 1500, 100, 0, 1, 0.5f, "", (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                CardArtRoller.infos.get(q.cardID).C = c.getValue();
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));
        public static SpireField<NonModButton> flipXButton = new SpireField<NonModButton>(() -> new NonModButton(1500, 600, (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                CardArtRoller.infos.get(q.cardID).flipX = !CardArtRoller.infos.get(q.cardID).flipX;
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));

        public static SpireField<NonModButton> randomizeButton = new SpireField<NonModButton>(() -> new NonModButton(1500, 700, (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                Random rng = new Random();
                CardArtRoller.infos.put(q.cardID, new CardArtRoller.ReskinInfo(CardArtRoller.infos.get(q.cardID).origCardID, rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.randomBoolean()));
                UIAdditions.hueSlider.get(CardCrawlGame.cardPopup).setValue(CardArtRoller.infos.get(q.cardID).H);
                UIAdditions.saturationSlider.get(CardCrawlGame.cardPopup).setValue(CardArtRoller.infos.get(q.cardID).S);
                UIAdditions.lightnessSlider.get(CardCrawlGame.cardPopup).setValue(CardArtRoller.infos.get(q.cardID).L);
                UIAdditions.contrastSlider.get(CardCrawlGame.cardPopup).setValue(CardArtRoller.infos.get(q.cardID).C);
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));

        public static SpireField<NonModButton> prevButton = new SpireField<NonModButton>(() -> new NonModButton(1400, 800, (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                int idx = stringAllCards(q.type).indexOf(CardArtRoller.infos.get(q.cardID).origCardID) - 1;
                if (idx != 0) {
                    CardArtRoller.infos.get(q.cardID).origCardID = stringAllCards(q.type).get(idx);
                }
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));

        public static SpireField<NonModButton> nextButton = new SpireField<NonModButton>(() -> new NonModButton(1600, 800, (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                CardArtRoller.doneCards.remove(q.cardID);
                int idx = stringAllCards(q.type).indexOf(CardArtRoller.infos.get(q.cardID).origCardID) + 1;
                if (idx != stringAllCards(q.type).size() - 1) {
                    CardArtRoller.infos.get(q.cardID).origCardID = stringAllCards(q.type).get(idx);
                }
                CardArtRoller.computeCard((AbstractEasyCard) q);
                Texture t = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg");
                t.dispose();
                ReflectionHacks.setPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "portraitImg", null);
                try {
                    Method m = SingleCardViewPopup.class.getDeclaredMethod("loadPortraitImg");
                    m.setAccessible(true);
                    m.invoke(CardCrawlGame.cardPopup);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }));
        public static SpireField<NonModButton> saveButton = new SpireField<NonModButton>(() -> new NonModButton(1500, 900, (c) -> {
            AbstractCard q = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (q instanceof AbstractEasyCard) {
                colorCardsPrefs.putString(q.cardID + "origID", CardArtRoller.infos.get(q.cardID).origCardID);
                colorCardsPrefs.putFloat(q.cardID + "hue", CardArtRoller.infos.get(q.cardID).H);
                colorCardsPrefs.putFloat(q.cardID + "sat", CardArtRoller.infos.get(q.cardID).S);
                colorCardsPrefs.putFloat(q.cardID + "lig", CardArtRoller.infos.get(q.cardID).L);
                colorCardsPrefs.putFloat(q.cardID + "con", CardArtRoller.infos.get(q.cardID).C);
                colorCardsPrefs.putBoolean(q.cardID + "flip", CardArtRoller.infos.get(q.cardID).flipX);
                colorCardsPrefs.flush();
            }
        }));
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render"
    )
    public static class RenderDataSliders {
        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard ___card = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (___card instanceof AbstractEasyCard) {
                PanelMinMaxSlider hueSlider = UIAdditions.hueSlider.get(__instance);
                PanelMinMaxSlider saturationSlider = UIAdditions.saturationSlider.get(__instance);
                PanelMinMaxSlider lightnessSlider = UIAdditions.lightnessSlider.get(__instance);
                PanelMinMaxSlider contrastSlider = UIAdditions.contrastSlider.get(__instance);
                NonModButton flipXButton = UIAdditions.flipXButton.get(__instance);
                NonModButton randButton = UIAdditions.randomizeButton.get(__instance);
                NonModButton prevButton = UIAdditions.prevButton.get(__instance);
                NonModButton nextButton = UIAdditions.nextButton.get(__instance);
                NonModButton saveButton = UIAdditions.saveButton.get(__instance);
                sb.setColor(Color.WHITE);
                hueSlider.render(sb);
                saturationSlider.render(sb);
                lightnessSlider.render(sb);
                contrastSlider.render(sb);
                flipXButton.render(sb);
                randButton.render(sb);
                prevButton.render(sb);
                nextButton.render(sb);
                saveButton.render(sb);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "updateInput"
    )
    public static class UpdateDataSliders {
        public static void Prefix(SingleCardViewPopup __instance) {
            AbstractCard ___card = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "card");
            if (___card instanceof AbstractEasyCard) {
                PanelMinMaxSlider hueSlider = UIAdditions.hueSlider.get(__instance);
                PanelMinMaxSlider saturationSlider = UIAdditions.saturationSlider.get(__instance);
                PanelMinMaxSlider lightnessSlider = UIAdditions.lightnessSlider.get(__instance);
                PanelMinMaxSlider contrastSlider = UIAdditions.contrastSlider.get(__instance);
                NonModButton flipXButton = UIAdditions.flipXButton.get(__instance);
                NonModButton randButton = UIAdditions.randomizeButton.get(__instance);
                NonModButton prevButton = UIAdditions.prevButton.get(__instance);
                NonModButton nextButton = UIAdditions.nextButton.get(__instance);
                NonModButton saveButton = UIAdditions.saveButton.get(__instance);
                hueSlider.update();
                saturationSlider.update();
                lightnessSlider.update();
                contrastSlider.update();
                flipXButton.update();
                randButton.update();
                prevButton.update();
                nextButton.update();
                saveButton.update();
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "open",
            paramtypez = {
                    AbstractCard.class,
                    CardGroup.class
            }
    )
    public static class Open {
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard ___card, CardGroup c) {
            if (___card instanceof AbstractEasyCard) {
                UIAdditions.hueSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).H);
                UIAdditions.saturationSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).S);
                UIAdditions.lightnessSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).L);
                UIAdditions.contrastSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).C);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "open",
            paramtypez = {
                    AbstractCard.class
            }
    )
    public static class OpenTwo {
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard ___card) {
            if (___card instanceof AbstractEasyCard) {
                UIAdditions.hueSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).H);
                UIAdditions.saturationSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).S);
                UIAdditions.lightnessSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).L);
                UIAdditions.contrastSlider.get(__instance).setValue(CardArtRoller.infos.get(___card.cardID).C);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "openPrev"
    )
    public static class OpenPrev {
        public static void Postfix(SingleCardViewPopup __instance, AbstractCard ___prevCard) {
            if (___prevCard instanceof AbstractEasyCard) {
                UIAdditions.hueSlider.get(__instance).setValue(CardArtRoller.infos.get(___prevCard.cardID).H);
                UIAdditions.saturationSlider.get(__instance).setValue(CardArtRoller.infos.get(___prevCard.cardID).S);
                UIAdditions.lightnessSlider.get(__instance).setValue(CardArtRoller.infos.get(___prevCard.cardID).L);
                UIAdditions.contrastSlider.get(__instance).setValue(CardArtRoller.infos.get(___prevCard.cardID).C);
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "openNext"
    )
    public static class OpenNext {

        public static void Postfix(SingleCardViewPopup __instance, AbstractCard ___nextCard) {
            if (___nextCard instanceof AbstractEasyCard) {
                UIAdditions.hueSlider.get(__instance).setValue(CardArtRoller.infos.get(___nextCard.cardID).H);
                UIAdditions.saturationSlider.get(__instance).setValue(CardArtRoller.infos.get(___nextCard.cardID).S);
                UIAdditions.lightnessSlider.get(__instance).setValue(CardArtRoller.infos.get(___nextCard.cardID).L);
                UIAdditions.contrastSlider.get(__instance).setValue(CardArtRoller.infos.get(___nextCard.cardID).C);
            }
        }
    }
}
