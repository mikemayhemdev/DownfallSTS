package sneckomod;

import automaton.AutomatonMod;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.cards.OctoChoiceCard;
import downfall.downfallMod;
import downfall.events.Serpent_Evil;
import downfall.util.CardIgnore;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import sneckomod.cards.*;
import sneckomod.cards.unknowns.UnknownClass;
import sneckomod.cards.unknowns.UnknownColorless;
import sneckomod.cards.unknowns.UnknownDexterity;
import sneckomod.cards.unknowns.UnknownStrength;
import sneckomod.events.BackToBasicsSnecko;
import sneckomod.events.D8;
import sneckomod.events.Serpent_Snecko;
import sneckomod.events.SuspiciousHouse;
import sneckomod.patches.BottledD8Patch;
import sneckomod.potions.CheatPotion;
import sneckomod.potions.DiceRollPotion;
import sneckomod.potions.MuddlingPotion;
import sneckomod.potions.OffclassReductionPotion;
import sneckomod.relics.*;
import sneckomod.util.SneckoSilly;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;
import static downfall.patches.EvilModeCharacterSelect.evilMode;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class SneckoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        //EditStringsSubscriber,
        //EditKeywordsSubscriber,
        SetUnlocksSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber {
    public static final String SHOULDER1 = "sneckomodResources/images/char/shoulder.png";
    public static final String SHOULDER2 = "sneckomodResources/images/char/shoulderR.png";
    public static final String CORPSE = "sneckomodResources/images/char/corpse.png";
    public static final String CARD_ENERGY_S = "sneckomodResources/images/512/card_snecko_orb.png";
    public static final String TEXT_ENERGY = "sneckomodResources/images/512/card_small_orb_snecko.png";
    private static final String ATTACK_S_ART = "sneckomodResources/images/512/bg_attack_snecko.png";
    private static final String SKILL_S_ART = "sneckomodResources/images/512/bg_skill_snecko.png";
    private static final String POWER_S_ART = "sneckomodResources/images/512/bg_power_snecko.png";
    private static final String ATTACK_L_ART = "sneckomodResources/images/1024/bg_attack_snecko.png";
    private static final String SKILL_L_ART = "sneckomodResources/images/1024/bg_skill_snecko.png";
    private static final String POWER_L_ART = "sneckomodResources/images/1024/bg_power_snecko.png";
    private static final String CARD_ENERGY_L = "sneckomodResources/images/1024/card_snecko_orb.png";
    private static final String CHARSELECT_BUTTON = "sneckomodResources/images/charSelect/button.png";
    private static final String CHARSELECT_PORTRAIT = "sneckomodResources/images/charSelect/portrait.png";
    public static Color placeholderColor = new Color(64F / 255F, 123F / 255F, 147F / 255F, 1);
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags UNKNOWN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags SNEKPROOF;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags RNG;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags BANNEDFORSNECKO;

    public static ArrayList<AbstractCard.CardColor> validColors;
    public static boolean pureSneckoMode = false;

    public static boolean openedStarterScreen = false;

    private static String modID;
    private static ArrayList<AbstractCard> statuses = new ArrayList<>();
    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

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
            //  if (cls.hasAnnotation(CardNoSeen.class)) {
            //      UnlockTracker.hardUnlockOverride(card.cardID);
            //  }
        }
    }

    public static AbstractCard getOffClassCard() {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> (c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || c.hasTag(AbstractCard.CardTags.HEALING) || c.hasTag(BANNEDFORSNECKO)));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getOffClassCardMatchingPredicate(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> (c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || !q.test(c) || c.hasTag(AbstractCard.CardTags.HEALING) || c.hasTag(BANNEDFORSNECKO)));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getSpecificClassCard(AbstractCard.CardColor color) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.color != color || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.type == STATUS || c.rarity == AbstractCard.CardRarity.SPECIAL || c.hasTag(AbstractCard.CardTags.HEALING) || c.hasTag(BANNEDFORSNECKO));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getRandomStatus() {
        Collections.shuffle(statuses, AbstractDungeon.cardRandomRng.random);
        return statuses.get(0);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheSnecko("the Snecko", TheSnecko.Enums.THE_SNECKO),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheSnecko.Enums.THE_SNECKO, getModID() + "Resources/images/charSelect/leaderboard.png");
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
        BaseMod.addRelic(new SuperSneckoEye(), RelicType.SHARED);
        BaseMod.addRelic(new SneckoTalon(), RelicType.SHARED);
        BaseMod.addRelic(new BlankCard(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new sneckomod.relics.D8(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.registerBottleRelic(BottledD8Patch.inD8, new sneckomod.relics.D8());
        BaseMod.addRelic(new BabySnecko(), RelicType.SHARED);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SneckoSilly());
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (AbstractCard.CardColor p : AbstractCard.CardColor.values()) {
            if (p != AbstractCard.CardColor.COLORLESS && p != AbstractCard.CardColor.CURSE && p != TheSnecko.Enums.SNECKO_CYAN) {
                AbstractCard q = new UnknownClass(p);
                BaseMod.addCard(q);
            }
        }
    }

    @Override
    public void receiveSetUnlocks() {

        unlocks0 = new CustomUnlockBundle(
                Cheat.ID, PureSnecko.ID, Rotation.ID
        );
        UnlockTracker.addCard(Cheat.ID);
        UnlockTracker.addCard(PureSnecko.ID);
        UnlockTracker.addCard(Rotation.ID);

        unlocks1 = new CustomUnlockBundle(
                UnknownColorless.ID, UnknownStrength.ID, UnknownDexterity.ID
        );
        UnlockTracker.addCard(UnknownColorless.ID);
        UnlockTracker.addCard(UnknownStrength.ID);
        UnlockTracker.addCard(UnknownDexterity.ID);

        unlocks2 = new CustomUnlockBundle(
                MixItUp.ID, Transmogrify.ID, GlitteringGambit.ID
        );
        UnlockTracker.addCard(MixItUp.ID);
        UnlockTracker.addCard(Transmogrify.ID);
        UnlockTracker.addCard(GlitteringGambit.ID);

        unlocks3 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                RareBoosterPack.ID, SleevedAce.ID, CleanMud.ID
        );
        UnlockTracker.addRelic(RareBoosterPack.ID);
        UnlockTracker.addRelic(SleevedAce.ID);
        UnlockTracker.addRelic(CleanMud.ID);

        unlocks4 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                SuperSneckoEye.ID, SneckoTalon.ID, BlankCard.ID
        );
        UnlockTracker.addRelic(SuperSneckoEye.ID);
        UnlockTracker.addRelic(SneckoTalon.ID);
        UnlockTracker.addRelic(BlankCard.ID);

        BaseMod.addUnlockBundle(unlocks0, TheSnecko.Enums.THE_SNECKO, 0);

        BaseMod.addUnlockBundle(unlocks1, TheSnecko.Enums.THE_SNECKO, 1);

        BaseMod.addUnlockBundle(unlocks2, TheSnecko.Enums.THE_SNECKO, 2);

        BaseMod.addUnlockBundle(unlocks3, TheSnecko.Enums.THE_SNECKO, 3);

        BaseMod.addUnlockBundle(unlocks4, TheSnecko.Enums.THE_SNECKO, 4);

    }

    public void addPotions() {

        BaseMod.addPotion(MuddlingPotion.class, Color.CYAN, Color.CORAL, Color.MAROON, MuddlingPotion.POTION_ID);
        BaseMod.addPotion(CheatPotion.class, Color.GRAY, Color.WHITE, Color.BLACK, CheatPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
        BaseMod.addPotion(DiceRollPotion.class, Color.CYAN, Color.WHITE, Color.BLACK, DiceRollPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
        BaseMod.addPotion(OffclassReductionPotion.class, Color.CYAN, Color.CORAL, Color.MAROON, OffclassReductionPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);

    }

    public void receivePostInitialize() {
        addPotions();

        BaseMod.addEvent(new AddEventParams.Builder(D8.ID, sneckomod.events.D8.class) //Event ID//
                //Event Character//
                .playerClass(TheSnecko.Enums.THE_SNECKO)
                .eventType(EventUtils.EventType.ONE_TIME)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(BackToBasicsSnecko.ID, BackToBasicsSnecko.class) //Event ID//
                //Event Character//
                .playerClass(TheSnecko.Enums.THE_SNECKO)
                //Existing Event to Override//
                .overrideEvent(BackToBasics.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Serpent_Snecko.ID, Serpent_Snecko.class) //Event ID//
                //Event Character//
                .playerClass(TheSnecko.Enums.THE_SNECKO)
                //Event Spawn Condition//
                .spawnCondition(() -> !evilMode)
                //Event ID to Override//
                .overrideEvent(Sssserpent.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Serpent_Snecko.ID, Serpent_Snecko.class) //Event ID//
                //Event Character//
                .playerClass(TheSnecko.Enums.THE_SNECKO)
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Serpent_Evil.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(SuspiciousHouse.ID, SuspiciousHouse.class) //Event ID//
                .playerClass(TheSnecko.Enums.THE_SNECKO)
                .dungeonID(TheCity.ID)
                .eventType(EventUtils.EventType.NORMAL)
                //Only in Evil if content sharing is disabled
                .spawnCondition(() -> (evilMode || downfallMod.contentSharing_events))
                .create());

        ArrayList<AbstractCard> tmp = CardLibrary.getAllCards();
        for (AbstractCard c : tmp) {
            if (c.type == AbstractCard.CardType.STATUS && !(c.hasTag(AutomatonMod.GOOD_STATUS))) {
                statuses.add(c);
            }
        }
    }

    @Override
    public void receiveStartGame() {
        openedStarterScreen = false;
        validColors = new ArrayList<>();
    }

    public static int choosingCharacters = -1;

    public static CardGroup colorChoices;

    public static void findAWayToTriggerThisAtGameStart() {
        if (AbstractDungeon.player instanceof TheSnecko) {
            choosingCharacters = 0;
            colorChoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard.CardColor r : AbstractCard.CardColor.values()) {
                colorChoices.addToTop(new OctoChoiceCard("UNVERIFIED", r.name() + " Cards", "bronzeResources/images/cards/BuggyMess.png", "Add " + r.name() + " cards to this Snecko draft.", r));
            }
            colorChoices.shuffle();
            AbstractDungeon.gridSelectScreen.open(colorChoices, 1, false, "Choose."); //TODO: Localize all of this
        }
    }
}