package sneckomod;

import automaton.AutomatonMod;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import downfall.cards.OctoChoiceCard;
import downfall.downfallMod;
import downfall.events.Serpent_Evil;
import downfall.util.CardIgnore;
import downfall.util.TextureLoader;
import expansioncontent.patches.CardColorEnumPatch;
import expansioncontent.patches.CenterGridCardSelectScreen;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import sneckomod.cards.*;
import sneckomod.cards.unknowns.*;
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

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.*;
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
        StartGameSubscriber,
        PostUpdateSubscriber {
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

    public static ArrayList<AbstractCard.CardColor> validColors = new ArrayList<>();
    public static ArrayList<UnknownClass> unknownClasses = new ArrayList<>();
    public static boolean pureSneckoMode = false;

    public static boolean openedStarterScreen = true;

    public static TextureAtlas.AtlasRegion overBannerAll;
    public static TextureAtlas.AtlasRegion overBanner0;
    public static TextureAtlas.AtlasRegion overBanner1;
    public static TextureAtlas.AtlasRegion overBanner2;
    public static TextureAtlas.AtlasRegion overBanner3;
    public static TextureAtlas.AtlasRegion overBannerAuto;
    public static TextureAtlas.AtlasRegion overBannerWoke;
    public static TextureAtlas.AtlasRegion overBannerBlock;
    public static TextureAtlas.AtlasRegion overBannerChamp;
    public static TextureAtlas.AtlasRegion overBannerCollector;
    public static TextureAtlas.AtlasRegion overBannerColorless;
    public static TextureAtlas.AtlasRegion overBannerCommonA;
    public static TextureAtlas.AtlasRegion overBannerCommonS;
    public static TextureAtlas.AtlasRegion overBannerDefect;
    public static TextureAtlas.AtlasRegion overBannerDex;
    public static TextureAtlas.AtlasRegion overBannerDonuDeca;
    public static TextureAtlas.AtlasRegion overBannerDraw;
    public static TextureAtlas.AtlasRegion overBannerEthereal;
    public static TextureAtlas.AtlasRegion overBannerExhaust;
    public static TextureAtlas.AtlasRegion overBannerGuardian;
    public static TextureAtlas.AtlasRegion overBannerHexa;
    public static TextureAtlas.AtlasRegion overBannerIronclad;
    public static TextureAtlas.AtlasRegion overBannerModded;
    public static TextureAtlas.AtlasRegion overBannerRareA;
    public static TextureAtlas.AtlasRegion overBannerRareP;
    public static TextureAtlas.AtlasRegion overBannerRareS;
    public static TextureAtlas.AtlasRegion overBannerSilent;
    public static TextureAtlas.AtlasRegion overBannerSlime;
    public static TextureAtlas.AtlasRegion overBannerStrength;
    public static TextureAtlas.AtlasRegion overBannerStrike;
    public static TextureAtlas.AtlasRegion overBannerTime;
    public static TextureAtlas.AtlasRegion overBannerUncommonA;
    public static TextureAtlas.AtlasRegion overBannerUncommonP;
    public static TextureAtlas.AtlasRegion overBannerUncommonS;
    public static TextureAtlas.AtlasRegion overBannerAnything;
    public static TextureAtlas.AtlasRegion overBannerVuln;
    public static TextureAtlas.AtlasRegion overBannerWatcher;
    public static TextureAtlas.AtlasRegion overBannerWeak;
    public static TextureAtlas.AtlasRegion overBannerX;
    public static TextureAtlas.AtlasRegion overBannerBoss;

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
        possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || c.hasTag(AbstractCard.CardTags.HEALING) || c.hasTag(BANNEDFORSNECKO));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getOffClassCardMatchingPredicate(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color == TheSnecko.Enums.SNECKO_CYAN || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.type == STATUS || !q.test(c) || c.hasTag(AbstractCard.CardTags.HEALING) || c.hasTag(BANNEDFORSNECKO));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getSpecificClassCard(AbstractCard.CardColor color) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color != color || c.type == CURSE || c.type == STATUS || c.rarity == AbstractCard.CardRarity.SPECIAL || c.hasTag(AbstractCard.CardTags.HEALING) || c.hasTag(BANNEDFORSNECKO));
        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getRandomStatus() {
        Collections.shuffle(statuses, AbstractDungeon.cardRandomRng.random);
        return statuses.get(0).makeCopy();
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
        BaseMod.addRelicToCustomPool(new SneckoCommon(), TheSnecko.Enums.SNECKO_CYAN);
        BaseMod.addRelicToCustomPool(new SneckoBoss(), TheSnecko.Enums.SNECKO_CYAN);
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
            if (p != AbstractCard.CardColor.COLORLESS && p != AbstractCard.CardColor.CURSE && p != TheSnecko.Enums.SNECKO_CYAN && p != CardColorEnumPatch.CardColorPatch.BOSS) {
                UnknownClass q = new UnknownClass(p);
                unknownClasses.add(q);
                AbstractUnknownCard.unknownClassReplacements.add(new ArrayList<>());
                BaseMod.addCard(q);
            }
        }
    }

    @Override
    public void receiveSetUnlocks() {

        downfallMod.registerUnlockSuite(
                Memorize.ID,
                PureSnecko.ID,
                Rotation.ID,

                UnknownColorless.ID,
                UnknownStrength.ID,
                UnknownDexterity.ID,

                MixItUp.ID,
                Transmogrify.ID,
                GlitteringGambit.ID,

                RareBoosterPack.ID,
                SleevedAce.ID,
                CleanMud.ID,

                SuperSneckoEye.ID,
                SneckoTalon.ID,
                BlankCard.ID,

                TheSnecko.Enums.THE_SNECKO
        );

    }

    public void addPotions() {

        BaseMod.addPotion(MuddlingPotion.class, Color.CYAN, Color.CORAL, Color.MAROON, MuddlingPotion.POTION_ID);
        BaseMod.addPotion(CheatPotion.class, Color.GRAY, Color.WHITE, Color.BLACK, CheatPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
        BaseMod.addPotion(DiceRollPotion.class, Color.CYAN, Color.WHITE, Color.BLACK, DiceRollPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
        BaseMod.addPotion(OffclassReductionPotion.class, Color.CYAN, Color.CORAL, Color.MAROON, OffclassReductionPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(MuddlingPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(CheatPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(DiceRollPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(OffclassReductionPotion.POTION_ID);
        }
    }

    public void receivePostInitialize() {
        addPotions();

        overBannerAll = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbanner.png");
        overBanner0 = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/0cost.png");
        overBanner1 = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/1cost.png");
        overBanner2 = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/2cost.png");
        overBanner3 = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/3cost.png");
        overBannerX = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/xcost.png");
        overBannerAuto = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/automaton.png");
        overBannerWoke = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/awakenedone.png");
        overBannerBlock = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/block.png");
        overBannerChamp = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/champ.png");
        overBannerCollector = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/collector.png");
        overBannerCommonA = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/commonAttack.png");
        overBannerCommonS = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/commonSkill.png");
        overBannerDefect = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/defect.png");
        overBannerDex = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/dexterity.png");
        overBannerDonuDeca = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/donudeca.png");
        overBannerDraw = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/draw.png");
        overBannerEthereal = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/ethereal.png");
        overBannerExhaust = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/exhaust.png");
        overBannerGuardian = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/guardian.png");
        overBannerHexa = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/hexaghost.png");
        overBannerIronclad = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/ironclad.png");
        overBannerModded = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/modded.png");
        overBannerRareA = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/rareAttack.png");
        overBannerRareP = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/rarePower.png");
        overBannerRareS = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/rareSkill.png");
        overBannerSilent = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/silent.png");
        overBannerSlime = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/slimeboss.png");
        overBannerStrength = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/strength.png");
        overBannerStrike = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/strike.png");
        overBannerTime = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/timeeater.png");
        overBannerUncommonA = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/uncommonAttack.png");
        overBannerUncommonP = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/uncommonPower.png");
        overBannerUncommonS = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/uncommonSkill.png");
        overBannerAnything = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/unknown.png");
        overBannerVuln = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/vulnerable.png");
        overBannerWatcher = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/watcher.png");
        overBannerWeak = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/weak.png");
        overBannerBoss = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/boss.png");
        overBannerColorless = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/colorless.png");

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
            if (c.color == AbstractCard.CardColor.COLORLESS && c.rarity != AbstractCard.CardRarity.SPECIAL) {
                AbstractUnknownCard.unknownColorlessReplacements.add(c.cardID);
            }
        }

        if (SneckoMod.validColors.size() == 0) {
            SneckoMod.resetUnknownsLists();
        }
    }

    public static void resetUnknownsLists() {
        for (int i = 0; i < 10; i++) {
            System.out.println("CLEARING UNKNOWN LISTS!!!");
        }
        validColors.clear();
        for (AbstractCard.CardColor c : AbstractCard.CardColor.values()) {
            if (c != AbstractCard.CardColor.CURSE && c != AbstractCard.CardColor.COLORLESS)
                validColors.add(c);
        }
        updateAllUnknownReplacements();
    }

    @Override
    public void receiveStartGame() {
        if (!CardCrawlGame.loadingSave) {
            openedStarterScreen = false;
            validColors = new ArrayList<>();
        }
    }

    public static int choosingCharacters = -1;

    public static CardGroup colorChoices;

    public static String getClassFromColor(AbstractCard.CardColor c) {
        for (AbstractPlayer p : CardCrawlGame.characterManager.getAllCharacters()) {
            if (p.getCardColor() == c) {
                return p.getLocalizedCharacterName();
            }
        }
        return c.name().toLowerCase();
    }

    public static void dualClassChoice() {
        colorChoices.shuffle();
        CardGroup charChoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        charChoices.addToTop(colorChoices.getTopCard());
        charChoices.addToTop(colorChoices.getNCardFromTop(1));
        AbstractDungeon.gridSelectScreen.open(charChoices, 1, false, CardCrawlGame.languagePack.getUIString("sneckomod:AtGameStart").TEXT[4]);
    }

    public static AbstractCard playerStartCardForEventFromColor(AbstractCard.CardColor c) {
        for (AbstractPlayer p : CardCrawlGame.characterManager.getAllCharacters()) {
            if (p.getCardColor() == c) {
                return p.getStartCardForEvent();
            }
        }
        return new Madness();
    }

    public static void findAWayToTriggerThisAtGameStart() {
        if (AbstractDungeon.player instanceof TheSnecko && !pureSneckoMode) {
            validColors.clear();
            choosingCharacters = 0;
            colorChoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard.CardColor r : AbstractCard.CardColor.values()) {
                if (r != AbstractCard.CardColor.CURSE && r != AbstractDungeon.player.getCardColor() && r != AbstractCard.CardColor.COLORLESS && r != CardColorEnumPatch.CardColorPatch.BOSS) {
                    String s = getClassFromColor(r);
                    AbstractCard q = playerStartCardForEventFromColor(r);
                    String[] strings = CardCrawlGame.languagePack.getUIString("sneckomod:AtGameStart").TEXT;
                    AbstractCard.CardType tv = SKILL;
                    if (q != null) {
                        tv = q.type;
                    }
                    CustomCard c = new OctoChoiceCard("UNVERIFIED", strings[0] + s + strings[1], "bronzeResources/images/cards/BuggyMess.png", strings[2] + s + strings[3], r, tv);
                    if (q != null && q.portrait != null)
                        c.portrait = q.portrait;
                    colorChoices.addToTop(c);
                }
            }
            CenterGridCardSelectScreen.centerGridSelect = true;
            dualClassChoice();
        }
    }

    public static void addToLists(AbstractUnknownCard c, ArrayList<Predicate<AbstractCard>> predList, ArrayList<ArrayList<String>> listList) {
        predList.add(c.myNeeds());
        if (c.myList() != null) {
            c.myList().clear();
            listList.add(c.myList());
        }
    }

    public static void updateAllUnknownReplacements() {

        //TODO - Ban any unknowns from the cardpool if their list size = 0

        ArrayList<Predicate<AbstractCard>> predList = new ArrayList<>();
        ArrayList<ArrayList<String>> listList = new ArrayList<>();

        addToLists(new Unknown(), predList, listList); // 0
        addToLists(new Unknown0Cost(), predList, listList);
        addToLists(new Unknown1Cost(), predList, listList);
        addToLists(new Unknown2Cost(), predList, listList);
        addToLists(new Unknown3Cost(), predList, listList);
        addToLists(new UnknownBlock(), predList, listList); // 5
        addToLists(new UnknownCommonAttack(), predList, listList);
        addToLists(new UnknownCommonSkill(), predList, listList);
        addToLists(new UnknownDexterity(), predList, listList);
        // addToLists(new UnknownEthereal(), predList, listList);
        addToLists(new UnknownExhaust(), predList, listList);
        addToLists(new UnknownRareAttack(), predList, listList); // 10
        addToLists(new UnknownRarePower(), predList, listList);
        addToLists(new UnknownRareSkill(), predList, listList);
        addToLists(new UnknownStrength(), predList, listList);
        addToLists(new UnknownStrike(), predList, listList);
        addToLists(new UnknownUncommonAttack(), predList, listList); // 15
        addToLists(new UnknownUncommonSkill(), predList, listList);
        addToLists(new UnknownUncommonPower(), predList, listList);
        addToLists(new UnknownVulnerable(), predList, listList);
        addToLists(new UnknownWeak(), predList, listList);
        addToLists(new UnknownX(), predList, listList);  // 20
        addToLists(new UnknownDraw(), predList, listList);

        for (AbstractUnknownCard q : SneckoMod.unknownClasses) {
            addToLists(q, predList, listList);
        }
        addToLists(new UnknownColorless(), predList, listList);
        addToLists(new UnknownBoss(), predList, listList);

        AbstractUnknownCard.updateReplacements(predList, listList);
    }

    @Override
    public void receivePostUpdate() {
        if (!SneckoMod.openedStarterScreen) {
            if (CardCrawlGame.isInARun() && downfallMod.readyToDoThing) {
                SneckoMod.findAWayToTriggerThisAtGameStart();
                SneckoMod.openedStarterScreen = true;
            }
        }
        if (SneckoMod.choosingCharacters > -1 && SneckoMod.choosingCharacters <= 2 && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !pureSneckoMode) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            SneckoMod.colorChoices.removeCard(c);
            SneckoMod.validColors.add(c.color);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            if (SneckoMod.choosingCharacters == 2) {
                SneckoMod.choosingCharacters = 3;
                CenterGridCardSelectScreen.centerGridSelect = false;
                SneckoMod.updateAllUnknownReplacements();
                AbstractDungeon.commonCardPool.group.removeIf(ii -> ii instanceof UnknownClass && !SneckoMod.validColors.contains(ii.color));
                AbstractDungeon.srcCommonCardPool.group.removeIf(ii -> ii instanceof UnknownClass && !SneckoMod.validColors.contains(ii.color));
            } else if (SneckoMod.choosingCharacters < 2) {
                SneckoMod.choosingCharacters += 1;
                SneckoMod.dualClassChoice();
            }
        }
    }
}