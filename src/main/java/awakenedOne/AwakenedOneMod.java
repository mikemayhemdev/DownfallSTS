package awakenedOne;

import automaton.AutomatonMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.cards.cardvars.SecondDamage;
import awakenedOne.cards.cardvars.SecondMagicNumber;
import awakenedOne.cards.cardvars.ThirdMagicNumber;
import awakenedOne.events.TheNestAwakened;
import awakenedOne.events.WingStatueAwakened;
import awakenedOne.patches.OnLoseEnergyPowerPatch;
import awakenedOne.potions.CultistsDelight;
import awakenedOne.potions.SacramentalWine;
import awakenedOne.potions.SneckoPowersPotion;
import awakenedOne.powers.EnemyHexedPower;
import awakenedOne.relics.*;
import awakenedOne.ui.AwakenedIcon;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.CardFilter;
import awakenedOne.util.Wiz;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import champ.ChampChar;
import champ.events.Colosseum_Evil_Champ;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.events.city.Nest;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.events.exordium.Sssserpent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;
import downfall.events.Serpent_Evil;
import downfall.events.TheNest_Evil;
import downfall.util.CardIgnore;
import downfall.util.TextureLoader;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import sneckomod.OffclassHelper;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.events.BackToBasicsSnecko;
import sneckomod.events.D8;
import sneckomod.events.Serpent_Snecko;
import sneckomod.events.SuspiciousHouse;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static awakenedOne.util.Wiz.atb;
import static downfall.patches.EvilModeCharacterSelect.evilMode;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class AwakenedOneMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber,
        StartGameSubscriber,
        OnPlayerTurnStartSubscriber,
        PostDungeonUpdateSubscriber, PostPlayerUpdateSubscriber {


    public static final String SHOULDER1 = "awakenedResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "awakenedResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "awakenedResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "awakenedResources/images/512/card_awakened_orb.png";
    public static final String TEXT_ENERGY = "awakenedResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "awakenedResources/images/512/bg_attack_awakened.png";
    private static final String SKILL_S_ART = "awakenedResources/images/512/bg_skill_awakened.png";
    private static final String POWER_S_ART = "awakenedResources/images/512/bg_power_awakened.png";
    private static final String ATTACK_L_ART = "awakenedResources/images/1024/bg_attack_awakened.png";
    private static final String SKILL_L_ART = "awakenedResources/images/1024/bg_skill_awakened.png";
    private static final String POWER_L_ART = "awakenedResources/images/1024/bg_power_awakened.png";
    private static final String CARD_ENERGY_L = "awakenedResources/images/1024/card_awakened_orb.png";
    private static final String CHARSELECT_BUTTON = "awakenedResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "awakenedResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(18F / 255F, 250F / 255F, 240F / 255F, 1);
    public static Color potionLabColor = new Color(18F / 255F, 250F / 255F, 240F / 255F, 1);

    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags DELVE;

    private static String modID = "awakened";

    public AwakenedOneMod() {
        BaseMod.subscribe(this);

        modID = "awakened";

        BaseMod.addColor(AwakenedOneChar.Enums.AWAKENED_BLUE, placeholderColor, placeholderColor, placeholderColor,
                placeholderColor, placeholderColor, placeholderColor, placeholderColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }


    public void receivePostInitialize() {

        BaseMod.addEvent(new AddEventParams.Builder(TheNestAwakened.ID, TheNestAwakened.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> AbstractDungeon.player instanceof AwakenedOneChar)
                //Event ID to Override//
                .overrideEvent(Nest.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WingStatueAwakened.ID, WingStatueAwakened.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> AbstractDungeon.player instanceof AwakenedOneChar)
                //Event ID to Override//
                .overrideEvent(GoldenWing.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
    }


    public static String makeBetaCardPath(String resourcePath) {
        return "awakenedResources/images/cards/joke/" + resourcePath;
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractAwakenedCard) {
            ((AbstractAwakenedCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
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
        AwakenedOneMod awakenedOneMod = new AwakenedOneMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = AwakenedOneMod.class.getProtectionDomain().getCodeSource().getLocation();
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

        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new AwakenedOneChar("The Awakened One", AwakenedOneChar.Enums.AWAKENED_ONE), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, AwakenedOneChar.Enums.AWAKENED_ONE);
        addPotions();
    }

    @Override
    public void receiveEditRelics() {
        //add awakened one relics here
        BaseMod.addRelicToCustomPool(new RippedDoll(), AwakenedOneChar.Enums.AWAKENED_BLUE); //starter
        BaseMod.addRelicToCustomPool(new ShreddedDoll(), AwakenedOneChar.Enums.AWAKENED_BLUE); //starter+
        BaseMod.addRelicToCustomPool(new KTRibbon(), AwakenedOneChar.Enums.AWAKENED_BLUE); //boss misc
        BaseMod.addRelicToCustomPool(new TomeOfPortalmancy(), AwakenedOneChar.Enums.AWAKENED_BLUE); //common
        BaseMod.addRelicToCustomPool(new EyeOfTheOccult(), AwakenedOneChar.Enums.AWAKENED_BLUE); //rare
        BaseMod.addRelicToCustomPool(new ZenerDeck(), AwakenedOneChar.Enums.AWAKENED_BLUE); //uncommon
        BaseMod.addRelicToCustomPool(new AbyssBlade(), AwakenedOneChar.Enums.AWAKENED_BLUE); //boss energy lose [e] refund
        BaseMod.addRelicToCustomPool(new StrengthBooster(), AwakenedOneChar.Enums.AWAKENED_BLUE); //uncommon
        //BaseMod.addRelicToCustomPool(new DeadBird(), AwakenedOneChar.Enums.AWAKENED_BLUE); //rare
        BaseMod.addRelicToCustomPool(new HexxBomb(), AwakenedOneChar.Enums.AWAKENED_BLUE); //shop

        BaseMod.addRelicToCustomPool(new MoonTalisman(), AwakenedOneChar.Enums.AWAKENED_BLUE); //rare relic to replace dead bird's rare #2 slot

        //shared relics
        BaseMod.addRelic(new CawingCask(), RelicType.SHARED); //rare shared
        BaseMod.addRelic(new VioletPlumage(), RelicType.SHARED); //shop shared
        BaseMod.addRelic(new ShardOfNowak(), RelicType.SHARED); //uncommon shared
        BaseMod.addRelic(new MiniBlackHole(), RelicType.SHARED);//common shared

        BaseMod.addRelic(new DeadBird(), RelicType.SHARED); //decided this was useful enough to be shared
    }

    public void addPotions() {
        BaseMod.addPotion(CultistsDelight.class, Color.BLUE, Color.NAVY, Color.YELLOW, CultistsDelight.POTION_ID, AwakenedOneChar.Enums.AWAKENED_ONE);
        BaseMod.addPotion(SacramentalWine.class, Color.PURPLE, Color.VIOLET, Color.MAROON, SacramentalWine.POTION_ID, AwakenedOneChar.Enums.AWAKENED_ONE);
        BaseMod.addPotion(SneckoPowersPotion.class, Color.CYAN, Color.TAN, Color.BLUE, SneckoPowersPotion.POTION_ID, AwakenedOneChar.Enums.AWAKENED_ONE);



        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(CultistsDelight.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(SacramentalWine.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(SneckoPowersPotion.POTION_ID);
        }
    }


    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(AwakenedIcon.get());

        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());

        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // CONTENT STUFF

    //Hex Stuff
    //the int begone is the amount of Hex being applied, m is the creature being Hexxed, and source is the
    //source of the Hex or if it's the player directly applying Hex. This is helpful for mostly Snecko relevant things.
    public static void HexCurse(int begone, AbstractCreature m, AbstractCreature source) {
        //if (!(m.hasPower(UltimateHexDebuff.POWER_ID))) {
            atb(new ApplyPowerAction(m, source, new EnemyHexedPower(m, begone), begone));
       // }
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        OrbitingSpells.atBattleStart();
        ConjureAction.conjuresThisCombat = 0;
        OnLoseEnergyPowerPatch.EnergyLostThisCombat = 0;
        ConjureAction.refreshedthisturn = false;

    }

    public void onVictory() {
        OrbitingSpells.empty();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        ConjureAction.refreshedthisturn = false;
    }

    @Override
    public void receivePostPlayerUpdate() {
        OrbitingSpells.update();
    }

    // What button? Button? There is no button, I've never heard of a button in my entire life.
    //private static AwakenButton becomeAwesomeButton;

    @Override
    public void receiveStartGame() {
      //  becomeAwesomeButton = new AwakenButton();
    }

    public static void renderCombatUiElements(SpriteBatch sb) {
        if (Wiz.isInCombat() && AbstractDungeon.player.chosenClass.equals(AwakenedOneChar.Enums.AWAKENED_ONE)) {
           // becomeAwesomeButton.render(sb);
        }
    }

    @Override
    public void receivePostDungeonUpdate() {
        if (Wiz.isInCombat() && AbstractDungeon.player.chosenClass.equals(AwakenedOneChar.Enums.AWAKENED_ONE)) {
           // becomeAwesomeButton.update();
        }
    }
}