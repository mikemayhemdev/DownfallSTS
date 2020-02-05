package sneckomod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import sneckomod.cards.DiceBoulder;
import sneckomod.cards.unknowns.UnknownClass;
import sneckomod.relics.*;
import sneckomod.util.SneckoSilly;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class SneckoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber {
    public static final String SHOULDER1 = "sneckomodResources/images/char/shoulder.png";
    public static final String SHOULDER2 = "sneckomodResources/images/char/shoulder2.png";
    public static final String CORPSE = "sneckomodResources/images/char/corpse.png";
    private static final String ATTACK_S_ART = "sneckomodResources/images/512/bg_attack_snecko.png";
    private static final String SKILL_S_ART = "sneckomodResources/images/512/bg_skill_snecko.png";
    private static final String POWER_S_ART = "sneckomodResources/images/512/bg_power_snecko.png";
    public static final String CARD_ENERGY_S = "sneckomodResources/images/512/card_snecko_orb.png";
    public static final String TEXT_ENERGY = "sneckomodResources/images/512/card_small_orb_snecko.png";
    private static final String ATTACK_L_ART = "sneckomodResources/images/1024/bg_attack_snecko.png";
    private static final String SKILL_L_ART = "sneckomodResources/images/1024/bg_skill_snecko.png";
    private static final String POWER_L_ART = "sneckomodResources/images/1024/bg_power_snecko.png";
    private static final String CARD_ENERGY_L = "sneckomodResources/images/1024/card_snecko_orb.png";
    private static final String CHARSELECT_BUTTON = "sneckomodResources/images/charSelect/button.png";
    private static final String CHARSELECT_PORTRAIT = "sneckomodResources/images/charSelect/portrait.png";
    private static String modID;

    public static Color placeholderColor = new Color(64F / 255F, 123F / 255F, 147F / 255F, 1);

    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags UNKNOWN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags SNEKPROOF;


    public SneckoMod() {
        BaseMod.subscribe(this);

        modID = "sneckomod";

        BaseMod.addColor(TheSnecko.Enums.SNECKO_CYAN, placeholderColor, placeholderColor, placeholderColor,
                placeholderColor, placeholderColor, placeholderColor, placeholderColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        SneckoMod sneckoMod = new SneckoMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheSnecko("the Snecko", TheSnecko.Enums.THE_SNECKO),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheSnecko.Enums.THE_SNECKO);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new SneckoSoul(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new CleanMud(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new ConfusingCodex(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new LoadedDie(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new RareBoosterPack(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new SleevedAce(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new SuperSneckoSoul(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new UnknownEgg(), TheSnecko.Enums.SNECKO_CYAN);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SneckoSilly());
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        BaseMod.addCard(new DiceBoulder(0));
        UnlockTracker.markCardAsSeen(DiceBoulder.ID);
        for (AbstractCard.CardColor p : AbstractCard.CardColor.values()) {
            if (p != AbstractCard.CardColor.COLORLESS && p != AbstractCard.CardColor.CURSE && p != TheSnecko.Enums.SNECKO_CYAN) {
                AbstractCard q = new UnknownClass(p);
                BaseMod.addCard(q);
            }
        }
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = SneckoMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());
            if (cls.hasAnnotation(CardIgnore.class)) {
                continue;
            }
            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }
            System.out.println(classInfo.getClassName());
            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();
            BaseMod.addCard(card);
            if (cls.hasAnnotation(CardNoSeen.class)) {
                UnlockTracker.hardUnlockOverride(card.cardID);
            } else {
                UnlockTracker.unlockCard(card.cardID);
            }
        }
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/Charstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID() + "", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static AbstractCard getOffClassCard() {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> (c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || c.hasTag(AbstractCard.CardTags.HEALING)));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getOffClassCardMatchingPredicate(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> (c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || !q.test(c) || c.hasTag(AbstractCard.CardTags.HEALING)));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getSpecificClassCard(AbstractCard.CardColor color) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.color != color || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.type == STATUS || c.rarity == AbstractCard.CardRarity.SPECIAL || c.hasTag(AbstractCard.CardTags.HEALING));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }
}