package hermit;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import downfall.util.CardIgnore;
import hermit.actions.MessageCaller;
import hermit.patches.EnumPatch;
import hermit.potions.BlackBile;
import hermit.potions.Eclipse;
import hermit.relics.*;
import hermit.rewards.BountyGold;
import hermit.util.CardFilter;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hermit.cards.*;
import hermit.characters.hermit;
import hermit.potions.Tonic;
import hermit.util.IDCheckDontTouchPls;
import hermit.util.KeywordWithProper;
import hermit.util.TextureLoader;
import hermit.variables.DefaultCustomVariable;
import hermit.variables.DefaultSecondMagicNumber;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import org.clapper.util.classutil.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import static basemod.BaseMod.gson;
import static basemod.BaseMod.loadCustomStrings;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "hermit:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class HermitMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        //EditStringsSubscriber,
        //EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber,
        OnStartBattleSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    //
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)
    //public static boolean[] activeTutorials = new boolean[]{true};
    public static Properties HermitModDefaultSettings = new Properties();
    public static boolean tackybypass = false;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Hermit";
    private static final String AUTHOR = "Team D-13"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    public static final Color HERMIT_YELLOW = CardHelper.getColor(254.0F, 223.0F, 0.0F);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "hermitResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "hermitResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "hermitResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "hermitResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "hermitResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "hermitResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "hermitResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "hermitResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "hermitResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "hermitResources/images/charSelect/HermitButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "hermitResources/images/charSelect/hermitSelect.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "hermitResources/images/char/hermit/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "hermitResources/images/char/hermit/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "hermitResources/images/char/hermit/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "hermitResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "hermitResources/images/char/hermit/Hermit.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "hermitResources/images/char/hermit/Hermit.json";

    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // Dead on card list juts swoocing right in.

    public static CardGroup deadList;

    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public HermitMod() {
        System.out.println("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        modID = ("hermit");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename hermitResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of theDefault with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.
        
        System.out.println("Done subscribing");
        
        System.out.println("Creating the color " + hermit.Enums.COLOR_YELLOW.toString());
        
        BaseMod.addColor(hermit.Enums.COLOR_YELLOW, HERMIT_YELLOW, HERMIT_YELLOW, HERMIT_YELLOW,
                HERMIT_YELLOW, HERMIT_YELLOW, HERMIT_YELLOW, HERMIT_YELLOW,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        System.out.println("Done creating the color");
        
        
        System.out.println("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()


    }


    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    /*
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = HermitMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        System.out.println("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        System.out.println("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    */

    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    /*
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = HermitMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = HermitMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

     */
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {

        HermitMod hermitMod = new HermitMod();

        /*

        try {
            for (int i = 0; i < activeTutorials.length; i++) { HermitModDefaultSettings.setProperty("activeTutorials" + i, "true"); }
            SpireConfig config = new SpireConfig("hermit", "HermitModConfig", HermitModDefaultSettings);
            for (int j = 0; j < activeTutorials.length; j++) { activeTutorials[j] = config.getBool("activeTutorials" + j); }
        } catch (IOException e) { e.printStackTrace(); }

         */
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        System.out.println("Beginning to edit characters. " + "Add " + hermit.Enums.HERMIT.toString());
        
        BaseMod.addCharacter(new hermit("the Hermit", hermit.Enums.HERMIT),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, hermit.Enums.HERMIT);
        
        receiveEditPotions();
        System.out.println("Added " + hermit.Enums.HERMIT.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        System.out.println("Loading badge image and mod options");
        
        // Load the Mod Badge
       // Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
      //  ModPanel settingsPanel = new ModPanel();
        
       // BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.registerCustomReward(
                EnumPatch.HERMIT_BOUNTY,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    return new BountyGold(rewardSave.amount);
                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null, ((BountyGold)customReward).goldAmt, 0);
                });

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // Dead On card list addition.

        // WIDE pots

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(Tonic.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(BlackBile.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(Eclipse.POTION_ID);
        }

        deadList = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard deadlist_c : CardLibrary.getAllCards()) {
            if (deadlist_c.hasTag(AbstractHermitCard.Enums.DEADON) && !deadlist_c.hasTag(AbstractCard.CardTags.HEALING) && deadlist_c.type != AbstractCard.CardType.CURSE && deadlist_c.rarity != AbstractCard.CardRarity.BASIC)
            {
                deadList.addToBottom(deadlist_c);
            }
        }

        // =============== /EVENTS/ =================
        System.out.println("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        System.out.println("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(Tonic.class, null,null,null, Tonic.POTION_ID, hermit.Enums.HERMIT);
        BaseMod.addPotion(BlackBile.class, null,null,null, BlackBile.POTION_ID, hermit.Enums.HERMIT);
        BaseMod.addPotion(Eclipse.class, Color.SCARLET.cpy(),Color.BLACK.cpy(),null, Eclipse.POTION_ID, hermit.Enums.HERMIT);

        System.out.println("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        System.out.println("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.

        BaseMod.addRelicToCustomPool(new Memento(), hermit.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new RyeStalk(), hermit.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new BartenderGlass(), hermit.Enums.COLOR_YELLOW);
        BaseMod.addRelic(new Horseshoe(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new Spyglass(), hermit.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new CharredGlove(), hermit.Enums.COLOR_YELLOW);
        BaseMod.addRelic(new RedScarf(), RelicType.SHARED);
        BaseMod.addRelic(new DentedPlate(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new PetGhost(), hermit.Enums.COLOR_YELLOW);
        BaseMod.addRelicToCustomPool(new ClaspedLocket(), hermit.Enums.COLOR_YELLOW);


        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        UnlockTracker.markRelicAsSeen(Memento.ID);
        UnlockTracker.markRelicAsSeen(RyeStalk.ID);
        UnlockTracker.markRelicAsSeen(BartenderGlass.ID);
        UnlockTracker.markRelicAsSeen(Horseshoe.ID);
        UnlockTracker.markRelicAsSeen(Spyglass.ID);
        UnlockTracker.markRelicAsSeen(CharredGlove.ID);
        UnlockTracker.markRelicAsSeen(RedScarf.ID);
        UnlockTracker.markRelicAsSeen(DentedPlate.ID);
        UnlockTracker.markRelicAsSeen(PetGhost.ID);
        UnlockTracker.markRelicAsSeen(ClaspedLocket.ID);
        System.out.println("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        System.out.println("Adding variables");
        //Ignore this
        //pathCheck();
        // Add the Custom Dynamic Variables
        System.out.println("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        System.out.println("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.


            try {
                autoAddCards();
            } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        System.out.println("Done adding cards!");
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = HermitMod.class.getProtectionDomain().getCodeSource().getLocation();
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
//            UnlockTracker.unlockCard(card.cardID);
            // if (cls.hasAnnotation(CardNoSeen.class)) {
            //     UnlockTracker.hardUnlockOverride(card.cardID);
            // }

        }
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================

    /*
    @Override
    public void receiveEditKeywords() {
        loadKeywords("eng");
        if (Settings.language != Settings.GameLanguage.ENG)
        {
            loadKeywords(Settings.language.toString().toLowerCase());
        }
    }


    private void loadKeywords(String langKey)
    {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + langKey).exists())
        {
            System.out.println("Hermit: Language not found: " + langKey);
            return;
        }

        for (KeywordWithProper keyword : gson.fromJson(GetLocString(langKey, "Keyword-Strings"), KeywordWithProper[].class))
        {
            BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);

            if (keyword.ID.equals("rugged")) {
                Tonic.keyword_name = keyword.PROPER_NAME;
                Tonic.keyword_description = keyword.DESCRIPTION;
            }

            if (keyword.ID.equals("bruise")) {
                BlackBile.keyword_name = keyword.PROPER_NAME;
                BlackBile.keyword_description = keyword.DESCRIPTION;
            }
        }
    }




    @Override
    public void receiveEditStrings() {
        loadStrings("eng");
        if (Settings.language != Settings.GameLanguage.ENG)
        {
            loadStrings(Settings.language.toString().toLowerCase());
        }
    }

    private void loadStrings(String langKey)
    {
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + langKey).exists())
        {
            System.out.println("Hermit: Language not found: " + langKey);
            return;
        }

        loadCustomStrings(CardStrings.class, GetLocString(langKey, "Card-Strings"));
        loadCustomStrings(RelicStrings.class, GetLocString(langKey, "Relic-Strings"));
        loadCustomStrings(PowerStrings.class, GetLocString(langKey, "Power-Strings"));
        loadCustomStrings(CharacterStrings.class, GetLocString(langKey, "Character-Strings"));
        loadCustomStrings(UIStrings.class, GetLocString(langKey, "UI-Strings"));
        loadCustomStrings(PotionStrings.class, GetLocString(langKey, "Potion-Strings"));
        loadCustomStrings(TutorialStrings.class, GetLocString(langKey, "Tutorial-Strings"));
    }


    private static String GetLocString(String locCode, String name) {
        return Gdx.files.internal(getModID() + "Resources/localization/" + locCode + "/HermitMod-" + name + ".json").readString(
                String.valueOf(StandardCharsets.UTF_8));
    }

     */

    public void receiveOnBattleStart(AbstractRoom room) {
        tackybypass = true;
        if (AbstractDungeon.player instanceof hermit) {
            if (downfallMod.unseenTutorials[0]){ AbstractDungeon.actionManager.addToBottom(new MessageCaller(0)); }
        }
    }


    public static void saveData() throws IOException {
            /*
        SpireConfig config = new SpireConfig("hermit", "HermitModConfig");
        int i;
        for (i = 0; i < activeTutorials.length; i++) { config.setBool("activeTutorials" + i, activeTutorials[i]); }
        config.save();
    */
    }


    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractHermitCard) {
            ((AbstractHermitCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = TextureLoader.getTexture(getModID() + "Resources/images/betacards/" + img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }

    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveAddAudio()
    {
        BaseMod.addAudio(makeID("GUN1"), "hermitResources/audio/hermit_gun.ogg");
        BaseMod.addAudio(makeID("GUN2"), "hermitResources/audio/hermit_gun2.ogg");
        BaseMod.addAudio(makeID("GUN3"), "hermitResources/audio/hermit_gun3.ogg");
        BaseMod.addAudio(makeID("SPIN"), "hermitResources/audio/hermit_spin.ogg");
        BaseMod.addAudio(makeID("RELOAD"), "hermitResources/audio/hermit_reload.ogg");
    }
}
