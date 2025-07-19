package sneckomod;

import automaton.AutomatonChar;
import automaton.AutomatonMod;
import automaton.cards.Deprecate;
import automaton.cards.Invalidate;
import automaton.cards.Undervolt;
import awakenedOne.AwakenedOneChar;
import awakenedOne.cards.*;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import champ.ChampChar;
import champ.cards.*;
import collector.CollectorChar;
import collector.cards.*;
import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.cards.OctoChoiceCard;
import downfall.downfallMod;
import downfall.events.Serpent_Evil;
import downfall.util.CardIgnore;
import downfall.util.TextureLoader;
import expansioncontent.cards.DashGenerateEvil;
import expansioncontent.cards.SuperLivingWall;
import expansioncontent.patches.CardColorEnumPatch;
import expansioncontent.patches.CenterGridCardSelectScreen;
import gremlin.cards.*;
import guardian.cards.*;
import hermit.cards.*;
import hermit.characters.hermit;
import hermit.util.Wiz;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import slimebound.cards.*;
import slimebound.cards.Dissolve;
import slimebound.patches.AbstractCardEnum;
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
import sneckomod.util.ArchetypeHelper;
import sneckomod.util.SneckoSilly;
import theHexaghost.TheHexaghost;
import theHexaghost.cards.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.*;
import static downfall.downfallMod.sneckoNoModCharacters;
import static downfall.patches.EvilModeCharacterSelect.evilMode;
import static sneckomod.util.ColorfulCardReward.TEXT;
import static theHexaghost.HexaMod.GHOSTWHEELCARD;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class SneckoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        SetUnlocksSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber,
        PostUpdateSubscriber,
        AddAudioSubscriber {
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
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags OVERFLOW;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags MUDDLED;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags NO_TYPHOON;

    public static Random identifyRng;

    public static ArrayList<AbstractCard.CardColor> validColors = new ArrayList<>();
    public static ArrayList<String> allowedColors = new ArrayList<>(Arrays.asList("RED", "BLUE", "GREEN", "PURPLE", "GUARDIAN", "SLIMEBOUND", "HEXA_GHOST_PURPLE", "THE_CHAMP_GRAY", "THE_BRONZE_AUTOMATON", "GREMLIN", "HERMIT_YELLOW", "THE_COLLECTOR", "AWAKENED_BLUE"));
    public static ArrayList<UnknownClass> unknownClasses = new ArrayList<>();
    public static boolean pureSneckoMode = false;

    public static boolean openedStarterScreen = true;

    public static HashMap<String, TextureAtlas.AtlasRegion> overBannerClasses = new HashMap<>();
    public static HashMap<UUID, String> unknownMap = new HashMap<>();

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
    public static TextureAtlas.AtlasRegion overBannerGremlins;
    public static TextureAtlas.AtlasRegion overBannerHermit;
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

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractSneckoCard) {
            ((AbstractSneckoCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = downfall.util.TextureLoader.getTexture("sneckomodResources/images/betacards/" + img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }


    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
    }

    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/" + resourcePath;
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
    // THE SUPER IMPORTANT GIFT ARRAY
    ArrayList<ArrayList<AbstractCard>> incomingGiftsList = new ArrayList<>();
    //




    @Deprecated
    public static AbstractCard getOffClassCard() {
        return OffclassHelper.getARandomOffclass();
    }

    private static ArrayList<String> simplePossibilities;

    public static AbstractCard getRandomAnyColorCard() {
        if (simplePossibilities == null) {
            simplePossibilities = new ArrayList<>();
            for (AbstractCard q : CardLibrary.getAllCards()) {
                if (!q.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && !q.hasTag(AbstractCard.CardTags.STARTER_DEFEND) && !q.hasTag(BANNEDFORSNECKO) &&
                        q.color != AbstractCard.CardColor.CURSE && q.type != CURSE && q.type != STATUS &&
                        !q.hasTag(AbstractCard.CardTags.HEALING) && q.rarity != AbstractCard.CardRarity.SPECIAL) {
                    simplePossibilities.add(q.cardID);
                }
            }
        }

        String selectedCardID = Wiz.getRandomItem(simplePossibilities, AbstractDungeon.cardRandomRng);
        return CardLibrary.getCopy(selectedCardID);
    }

    //removed to-do here, rarity is not relevant due to how gift generation works in a limited pool

    public static AbstractCard getOffClassCardMatchingPredicate(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color == AbstractDungeon.player.getCardColor() || c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.rarity == AbstractCard.CardRarity.BASIC || c.type == STATUS || !q.test(c)  || c.hasTag(BANNEDFORSNECKO) || c.hasTag(GHOSTWHEELCARD));
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.HEALING));
        }
        if ((!pureSneckoMode && !AbstractDungeon.player.hasRelic(PrismaticShard.ID) && AbstractDungeon.player instanceof TheSnecko));
            possList.removeIf(c -> !validColors.contains(c.color));

        possList.removeIf(c -> c.color == AbstractDungeon.player.getCardColor());

        possList.removeIf(c -> c.color == CardColorEnumPatch.CardColorPatch.BOSS);

        possList.removeIf(c -> c.color == AbstractCard.CardColor.COLORLESS);

        possList.removeIf(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE);

        if (possList.isEmpty()) {
            return new Madness();
        }

        return possList.get(AbstractDungeon.miscRng.random(possList.size() - 1)).makeCopy();
    }

    public static AbstractCard getOffClassCardMatchingPredicatePotionRng(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color == AbstractDungeon.player.getCardColor() || c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.rarity == AbstractCard.CardRarity.BASIC || c.type == STATUS || !q.test(c)  || c.hasTag(BANNEDFORSNECKO) || c.hasTag(GHOSTWHEELCARD));
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.HEALING));
        }
        if ((!pureSneckoMode && !AbstractDungeon.player.hasRelic(PrismaticShard.ID) && AbstractDungeon.player instanceof TheSnecko));
        possList.removeIf(c -> !validColors.contains(c.color));

        possList.removeIf(c -> c.color == AbstractDungeon.player.getCardColor());

        possList.removeIf(c -> c.color == CardColorEnumPatch.CardColorPatch.BOSS);

        possList.removeIf(c -> c.color == AbstractCard.CardColor.COLORLESS);

        possList.removeIf(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE);

        if (possList.isEmpty()) {
            return new Madness();
        }

        return possList.get(AbstractDungeon.potionRng.random(possList.size() - 1)).makeCopy();
    }


    public static AbstractCard getOffClassCardMatchingPredicateRelicRng(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color == AbstractDungeon.player.getCardColor() || c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.rarity == AbstractCard.CardRarity.BASIC || c.type == STATUS || !q.test(c)  || c.hasTag(BANNEDFORSNECKO) || c.hasTag(GHOSTWHEELCARD));
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.HEALING));
        }
        if ((!pureSneckoMode && !AbstractDungeon.player.hasRelic(PrismaticShard.ID) && AbstractDungeon.player instanceof TheSnecko));
        possList.removeIf(c -> !validColors.contains(c.color));

        possList.removeIf(c -> c.color == AbstractDungeon.player.getCardColor());

        possList.removeIf(c -> c.color == CardColorEnumPatch.CardColorPatch.BOSS);

        possList.removeIf(c -> c.color == AbstractCard.CardColor.COLORLESS);

        possList.removeIf(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE);

        if (possList.isEmpty()) {
            return new Madness();
        }

        return possList.get(AbstractDungeon.relicRng.random(possList.size() - 1)).makeCopy();
    }


    //may God forgive me for this great sin.
    public static AbstractCard getOffClassCardMatchingPredicateDebuff(Predicate<AbstractCard> q) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());
        possList.removeIf(c -> (!(ArchetypeHelper.appliesDebuff(c) ||
                // Ironclad Cards
                (c.cardID.equals(Disarm.ID) ||
                 // Silent Cards
                c.cardID.equals(PiercingWail.ID) ||
                c.cardID.equals(Catalyst.ID) ||
                c.cardID.equals(NoxiousFumes.ID) ||
                c.cardID.equals(Envenom.ID) ||
                c.cardID.equals(Malaise.ID) ||
                //Defect Cards
                c.cardID.equals(GoForTheEyes.ID) ||
                c.cardID.equals(BeamCell.ID) ||
                c.cardID.equals(LockOn.ID) ||
                // Watcher Cards
                c.cardID.equals(CrushJoints.ID) ||
                c.cardID.equals(SashWhip.ID) ||
                c.cardID.equals(Indignation.ID) ||
                c.cardID.equals(WaveOfTheHand.ID) ||
                //Colorless Cards
                c.cardID.equals(Trip.ID) ||
                c.cardID.equals(Blind.ID) ||
                c.cardID.equals(DarkShackles.ID) ||
                c.cardID.equals(SadisticNature.ID) ||
                //Gremlins Cards
                c.cardID.equals(Jeer.ID) ||
                c.cardID.equals(AggressiveDefense.ID) ||
                c.cardID.equals(Dazzle.ID) ||
                c.cardID.equals(Exacerbate.ID) ||
                c.cardID.equals(BrokenShin.ID) ||
                c.cardID.equals(Heckle.ID) ||
                c.cardID.equals(ToeStub.ID) ||
                //Champ Cards
                c.cardID.equals(ViciousMockery.ID) ||
                c.cardID.equals(Taunt.ID) ||
                c.cardID.equals(FaceSlap.ID) ||
                c.cardID.equals(Shatter.ID) ||
                c.cardID.equals(SetATrap.ID) ||
                c.cardID.equals(CheapShot.ID) ||
                //Slime Boss Cards
                c.cardID.equals(LivingWall.ID) ||
                c.cardID.equals(ItLooksTasty.ID) ||
                c.cardID.equals(SplitLicking.ID) ||
                c.cardID.equals(Gluttony.ID) ||
                c.cardID.equals(HungryTackle.ID) ||
                c.cardID.equals(DisruptingSlam.ID) ||
                c.cardID.equals(Nibble.ID) ||
                c.cardID.equals(Dissolve.ID) ||
                c.cardID.equals(Recollect.ID) ||
                c.cardID.equals(Recycling.ID) ||
                c.cardID.equals(WasteNot.ID) ||
                //Boss Card Pool
                c.cardID.equals(SuperLivingWall.ID) ||
                c.cardID.equals(DashGenerateEvil.ID) ||
                c.cardID.equals(expansioncontent.cards.GoopSpray.ID) ||
                c.cardID.equals(expansioncontent.cards.YouAreMine.ID) ||
                c.cardID.equals(expansioncontent.cards.FaceSlap.ID) ||
                //Collector Cards
                c.cardID.equals(YouAreMine.ID) ||
                c.cardID.equals(SapStrength.ID) ||
                c.cardID.equals(CursedWail.ID) ||
                c.cardID.equals(SeedOfDoubt.ID) ||
                c.cardID.equals(ThornWhip.ID) ||
                c.cardID.equals(Arrogance.ID) ||
                c.cardID.equals(Invigorate.ID) ||
                c.cardID.equals(ItMattersNot.ID) ||
                c.cardID.equals(Billow.ID) ||
                c.cardID.equals(BindingCall.ID) ||
                c.cardID.equals(CantTouchThis.ID) ||
                c.cardID.equals(RotwoodKindling.ID) ||
                c.cardID.equals(Suffering.ID) ||
                c.cardID.equals(DarkLordForm.ID) ||
                c.cardID.equals(Darkstorm.ID) ||
                c.cardID.equals(Goodbye.ID) ||
                c.cardID.equals(Condemn.ID) ||
                c.cardID.equals(InevitableDemise.ID) ||
                c.cardID.equals(BlackBindings.ID) ||
                c.cardID.equals(MiniCurse.ID) ||
                c.cardID.equals(Finalize.ID) ||
                c.cardID.equals(InflictAgony.ID) ||
                c.cardID.equals(LanternFlare.ID) ||
                //Hexa Cards
                c.cardID.equals(Sear.ID) ||
                c.cardID.equals(HeatMetal.ID) ||
                c.cardID.equals(BurningTouch.ID) ||
                c.cardID.equals(Firestarter.ID) ||
                c.cardID.equals(FlamesFromBeyond.ID) ||
                c.cardID.equals(SpectralSpark.ID) ||
                c.cardID.equals(GhostflameInferno.ID) ||
                c.cardID.equals(Incineration.ID) ||
                c.cardID.equals(LingeringShades.ID) ||
                c.cardID.equals(VolcanoVisage.ID) ||
                //Guardian Cards
                c.cardID.equals(SentryBeam.ID) ||
                c.cardID.equals(Gem_Purple.ID) ||
                c.cardID.equals(Gem_Crimson.ID) ||
                //Hermit Cards
                c.cardID.equals(Brawl.ID) ||
                c.cardID.equals(RoundhouseKick.ID) ||
                //Automaton Cards
                 c.cardID.equals(Deprecate.ID) ||
                 c.cardID.equals(Invalidate.ID) ||
                 c.cardID.equals(Undervolt.ID) ||
                //Snecko Cards
                c.cardID.equals(Deception.ID) ||
                c.cardID.equals(MakeshiftBlade.ID) ||
                c.cardID.equals(Belittle.ID) ||
                c.cardID.equals(PoisonParadise.ID) ||
                c.cardID.equals(AceOfWands.ID) ||

                //Awakened One Cards
                c.cardID.equals(ManaburnCard.ID) ||
                c.cardID.equals(SingularityShield.ID) ||
                c.cardID.equals(Deathwish.ID) ||
                c.cardID.equals(FromTheAbyss.ID) ||
                c.cardID.equals(MirePit.ID) ||
                c.cardID.equals(Sludge.ID) ||
                c.cardID.equals(SongOfSorrow.ID) ||
                c.cardID.equals(SplitWide.ID) ||
                c.cardID.equals(SheerTerror.ID) ||
                c.cardID.equals(ShroudOfMiasma.ID) ||
                c.cardID.equals(ExNihilo.ID)
                )

        ) || (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color == AbstractDungeon.player.getCardColor() || c.color == AbstractCard.CardColor.CURSE || c.type == CURSE || c.rarity == AbstractCard.CardRarity.SPECIAL || c.rarity == AbstractCard.CardRarity.BASIC || c.type == STATUS || !q.test(c)  || c.hasTag(BANNEDFORSNECKO) || c.hasTag(GHOSTWHEELCARD))
        || (
                //False Positives
                        c.cardID.equals(SwordThrow.ID) ||
                        c.cardID.equals(ShieldThrow.ID) ||
                        c.cardID.equals(HyperBeam_Guardian.ID) ||
                        c.cardID.equals(HoleUp.ID) ||
                        c.cardID.equals(Quickdraw.ID) ||
                        c.cardID.equals(Gestalt.ID) ||
                        c.cardID.equals(OverwhelmingPower.ID) ||
                        c.cardID.equals(RadiantFlame.ID) ||
                        c.cardID.equals(DecasProtection.ID) ||
                        c.cardID.equals(BulletTime.ID) ||
                        c.cardID.equals(WraithForm.ID) ||
                        c.cardID.equals(AdrenalArmor.ID) ||
                        c.cardID.equals(BattleTrance.ID) ||
                        c.cardID.equals(Berserk.ID) ||
                        c.cardID.equals(Flex.ID) ||
                        c.cardID.equals(BiasedCognition.ID) ||
                        c.cardID.equals(Hyperbeam.ID) ||
                        c.cardID.equals(Fasting.ID) ||
                        c.cardID.equals(Erupt.ID) ||
                        c.cardID.equals(MachineLearning.ID) ||
                        c.cardID.equals(GremlinMeal.ID) ||
                        c.cardID.equals(FollowThrough.ID) ||
                        c.cardID.equals(FeatherVeil.ID) ||
                        c.cardID.equals(FleetingCuriosity.ID) ||
                        c.cardID.equals(DemonGlyph.ID) ||
                        c.cardID.equals(SacrilegiousStrike.ID) ||
                        c.cardID.equals(Unleash.ID) ||

                        //Really Bad Offclass cards
                        c.cardID.equals(ChargedBarrage.ID)
        ))
        );
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.HEALING));
        }
        if ((!pureSneckoMode && !AbstractDungeon.player.hasRelic(PrismaticShard.ID) && AbstractDungeon.player instanceof TheSnecko));
        possList.removeIf(c -> !validColors.contains(c.color));

        possList.removeIf(c -> (
                //crossmod restriction pls work
                c.color != AbstractCard.CardColor.RED &&
                c.color != AbstractCard.CardColor.GREEN &&
                c.color != AbstractCard.CardColor.BLUE &&
                c.color != AbstractCard.CardColor.PURPLE &&
                c.color != AbstractCard.CardColor.COLORLESS &&
                c.color != CardColorEnumPatch.CardColorPatch.BOSS &&
                //Downfall Characters
                c.color != hermit.Enums.COLOR_YELLOW &&
                //Act 1 Bosses
                c.color != AbstractCardEnum.SLIMEBOUND &&
                c.color != guardian.patches.AbstractCardEnum.GUARDIAN &&
                c.color != TheHexaghost.Enums.GHOST_GREEN &&
                //Act 2 Bosses
                c.color != ChampChar.Enums.CHAMP_GRAY &&
                c.color != AutomatonChar.Enums.BRONZE_AUTOMATON &&
                c.color != CollectorChar.Enums.COLLECTOR &&
                //Act 3 Bosses
                c.color != AwakenedOneChar.Enums.AWAKENED_BLUE &&

                //Bonus Characters
                c.color != TheSnecko.Enums.SNECKO_CYAN &&
                c.color != gremlin.patches.AbstractCardEnum.GREMLIN)

        &&
                //doesn't detect debuff application
                (!ArchetypeHelper.appliesDebuff(c) ||
                //detects debuff application, BUT is not targeted at an enemy
                (ArchetypeHelper.appliesDebuff(c) &&
                c.target != AbstractCard.CardTarget.ENEMY &&
                c.target != AbstractCard.CardTarget.SELF_AND_ENEMY &&
                c.target != AbstractCard.CardTarget.ALL_ENEMY)
        ));


        //PLEASE WORK
        possList.removeIf(c -> c.color == AbstractDungeon.player.getCardColor());
        possList.removeIf(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE);

        possList.removeIf(c -> c.color == CardColorEnumPatch.CardColorPatch.BOSS);

        possList.removeIf(c -> c.color == AbstractCard.CardColor.COLORLESS);

        if (possList.isEmpty()) {
            return new Madness();
        }

        return possList.get(AbstractDungeon.miscRng.random(possList.size() - 1)).makeCopy();
    }



    public static AbstractCard getSpecificClassCard(AbstractCard.CardColor color) {
        ArrayList<AbstractCard> possList = new ArrayList<>(CardLibrary.getAllCards());

        if (color != CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE) {
            possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.color != color || c.type == CURSE || c.type == STATUS || c.rarity == AbstractCard.CardRarity.SPECIAL || c.hasTag(BANNEDFORSNECKO) || c.hasTag(GHOSTWHEELCARD));
        }

        if (color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE) {
            ArrayList<AbstractCard> allCollecteds = Wiz.getCardsMatchingPredicate(c -> c.color == CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE && c.rarity != AbstractCard.CardRarity.SPECIAL && !c.hasTag(AbstractCard.CardTags.HEALING), true);
            return (allCollecteds.remove(AbstractDungeon.cardRandomRng.random(allCollecteds.size() - 1)));
        }

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            possList.removeIf(c -> c.hasTag(AbstractCard.CardTags.HEALING));
        }

        if (possList.size() == 0) {
            possList.add(new Madness());
        }

        return possList.get(AbstractDungeon.cardRandomRng.random(possList.size() - 1)).makeCopy();
    }

    @Deprecated
    public static AbstractCard getRandomStatus() {
        return OffclassHelper.getARandomStatus();
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
        BaseMod.addRelicToCustomPool(new CrystallizedMud(), TheSnecko.Enums.SNECKO_CYAN);
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
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (AbstractCard.CardColor p : AbstractCard.CardColor.values()) {
            if (p != AbstractCard.CardColor.COLORLESS && p != AbstractCard.CardColor.CURSE && p != TheSnecko.Enums.SNECKO_CYAN && p != CardColorEnumPatch.CardColorPatch.BOSS && p != CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE) {
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
                SoulDraw.ID,
                Restock.ID,
                PureSnecko.ID,

                LatchOn.ID,
                SerpentineSleuth.ID,
                SerpentsNest.ID,

                TrashCan.ID,
                TrashToTreasure.ID,
                OverwhelmingPresence.ID,

                SneckoBoss.ID,
                SleevedAce.ID,
                CleanMud.ID,

                BlankCard.ID,
                SneckoTalon.ID,
                SleevedAce.ID,

                TheSnecko.Enums.THE_SNECKO
        );

    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(makeID("BUS"), makeAudioPath("bus.ogg"));
        BaseMod.addAudio(makeID("SHOTGUN"), makeAudioPath("shotgun.ogg"));
    }

    public void addPotions() {
        BaseMod.addPotion(CheatPotion.class, Color.GRAY, Color.WHITE, Color.BLACK, CheatPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
        BaseMod.addPotion(DiceRollPotion.class, Color.CYAN, Color.WHITE, Color.BLACK, DiceRollPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
        BaseMod.addPotion(OffclassReductionPotion.class, Color.CYAN, Color.CORAL, Color.MAROON, OffclassReductionPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
       // BaseMod.addPotion(MuddlingPotion.class, Color.CYAN, Color.CORAL, Color.MAROON, OffclassReductionPotion.POTION_ID, TheSnecko.Enums.THE_SNECKO);
//        BanSharedContentPatch.registerRunLockedPotion(TheSnecko.Enums.THE_SNECKO, MuddlingPotion.POTION_ID);

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
        overBannerGremlins = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/gremlins.png");
        overBannerHermit = TextureLoader.getTextureAsAtlasRegion("sneckomodResources/images/cardicons/overbannerIcons/hermit.png");
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
                //Event ID to Override//
                .overrideEvent(Sssserpent.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Serpent_Snecko.ID, Serpent_Snecko.class) //Event ID//
                //Event Character//
                .playerClass(TheSnecko.Enums.THE_SNECKO)
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
                OffclassHelper.addStatusToList(c);
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
            if (c != AbstractCard.CardColor.CURSE && c != AbstractCard.CardColor.COLORLESS && c != CardColorEnumPatch.CardColorPatch.BOSS && c != CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE)
                validColors.add(c);
        }
        OffclassHelper.updateAllUnknownReplacements();
    }

    @Override
    public void receiveStartGame() {
        if (!CardCrawlGame.loadingSave || (validColors.isEmpty() && !pureSneckoMode)) {
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
                if (r != AbstractCard.CardColor.CURSE && r != AbstractDungeon.player.getCardColor() && r != AbstractCard.CardColor.COLORLESS && r != CardColorEnumPatch.CardColorPatch.BOSS && r != CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE && (!sneckoNoModCharacters || allowedColors.contains(r.name()))) {
                    if (1==1) {

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
            }
            CenterGridCardSelectScreen.centerGridSelect = true;
            dualClassChoice();
        } else {
            resetUnknownsLists();
        }
    }

    @Deprecated
    public static void updateAllUnknownReplacements() {
        OffclassHelper.updateAllUnknownReplacements();
    }

    @Override
    public void receivePostUpdate() {
        gifted = false;
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
                OffclassHelper.updateAllUnknownReplacements();
                AbstractDungeon.commonCardPool.group.removeIf(ii -> ii instanceof UnknownClass && !SneckoMod.validColors.contains(ii.color));
                AbstractDungeon.srcCommonCardPool.group.removeIf(ii -> ii instanceof UnknownClass && !SneckoMod.validColors.contains(ii.color));
            } else if (SneckoMod.choosingCharacters < 2) {
                SneckoMod.choosingCharacters += 1;
                SneckoMod.dualClassChoice();
            }
        }
    }

    public static boolean gifted = false;

    public static ArrayList<ArrayList<AbstractCard>> incomingPicks = new ArrayList<>();

    public static void addGift(ArrayList<AbstractCard> incomingGift) {
        incomingPicks.add(incomingGift);
    }

    public static void nextGift() {
        System.out.println("Next called" + incomingPicks.size());
        if (incomingPicks.size() > 0 && !gifted) {
            gifted = true;
            System.out.println(incomingPicks.get(0));
            AbstractDungeon.cardRewardScreen.open(incomingPicks.remove(0), null, TEXT[2]);
        }
    }
}
