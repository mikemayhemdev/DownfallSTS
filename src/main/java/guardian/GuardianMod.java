package guardian;

import automaton.AutomatonChar;
import automaton.cards.*;
import automaton.relics.*;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import champ.ChampChar;
import champ.events.BackToBasicsChamp;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import com.megacrit.cardcrawl.events.shrines.PurificationShrine;
import com.megacrit.cardcrawl.events.shrines.Transmogrifier;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrine;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import downfall.cards.curses.Aged;
import downfall.downfallMod;
import downfall.potions.CursedFountainPotion;
import guardian.cards.*;
import guardian.cards.BronzeArmor;
import guardian.cards.BronzeOrb;
import guardian.characters.GuardianCharacter;
import guardian.events.StasisEgg;
import guardian.events.*;
import guardian.helpers.MultihitVariable;
import guardian.helpers.SecondaryMagicVariable;
import guardian.orbs.StasisOrb;
import guardian.patches.AbstractCardEnum;
import guardian.patches.BottledStasisPatch;
import guardian.patches.GuardianEnum;
import guardian.patches.RewardItemTypePatch;
import guardian.potions.AcceleratePotion;
import guardian.potions.BlockOnCardUsePotion;
import guardian.potions.DefensiveModePotion;
import guardian.potions.StasisDiscoveryPotion;
import guardian.powers.ExhaustStatusesPower;
import guardian.powers.zzz.MultiBoostPower;
import guardian.relics.*;
import guardian.rewards.GemReward;
import guardian.rewards.GemRewardAllRarities;
import guardian.stances.DefensiveMode;
import guardian.ui.EnhanceBonfireOption;
import guardian.vfx.SocketGemEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

import static downfall.patches.EvilModeCharacterSelect.evilMode;


@SpireInitializer
public class GuardianMod implements PostDrawSubscriber,
        PreMonsterTurnSubscriber,
        SetUnlocksSubscriber,
        PostDungeonInitializeSubscriber,
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        PostBattleSubscriber,
        AddAudioSubscriber,
        OnPlayerLoseBlockSubscriber
        //basemod.interfaces.EditKeywordsSubscriber
        //EditStringsSubscriber
{

    public static final Float stasisCardRenderScale = 0.2F;
    public static final Logger logger = LogManager.getLogger(GuardianMod.class.getName());
    private static final String ATTACK_CARD = "512/bg_attack_guardian.png";
    private static final String SKILL_CARD = "512/bg_skill_guardian.png";
    private static final String POWER_CARD = "512/bg_power_guardian.png";
    private static final String ENERGY_ORB = "512/card_guardian_orb.png";
    private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";
    private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack_guardian.png";
    private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill_guardian.png";
    private static final String POWER_CARD_PORTRAIT = "1024/bg_power_guardian.png";
    private static final String ENERGY_ORB_PORTRAIT = "1024/card_guardian_orb.png";
    public static GuardianCharacter guardianCharacter;
    public static Color mainGuardianColor = new Color(0.58F, 0.49F, 0.33F, 1.0F);
    private static final com.badlogic.gdx.graphics.Color GUARDIAN_COLOR = mainGuardianColor;
    public static ArrayList<Texture> socketTextures = new ArrayList<>();
    public static ArrayList<Texture> socketTextures2 = new ArrayList<>();
    public static ArrayList<Texture> socketTextures3 = new ArrayList<>();
    public static ArrayList<Texture> socketTextures4 = new ArrayList<>();
    public static boolean stasisDelay = false;
    public static int stasisCount;
    public static boolean discoveryOverride = false;
    public static boolean discoveryOverrideUpgrade = false;
    public static EnhanceBonfireOption socketBonfireOption;
    public static boolean talked1 = false;
    public static boolean talked2 = false;
    public static boolean talked3 = false;
    public static boolean talked4 = false;
    public static boolean talked5 = false;
    public static boolean talked6 = false;
    public static boolean talked7 = false;
    public static boolean talked8 = false;
    public static boolean talked9 = false;
    public static boolean gridScreenForGems = false;
    public static boolean gridScreenForSockets = false;
    public static boolean gridScreenInGrimForge = false;

    public static SocketGemEffect currSocketGemEffect = null;


    private static String modID;

    //public static BronzeOrbWhoReallyLikesDefectForSomeReason bronzeOrbInPlay;

    @SpireEnum
    public static AbstractCard.CardTags GEM;
    @SpireEnum
    public static AbstractCard.CardTags SELFSTASIS;
    @SpireEnum
    public static AbstractCard.CardTags SELFSTASISONCE;
    @SpireEnum
    public static AbstractCard.CardTags STASISGLOW;
    @SpireEnum
    public static AbstractCard.CardTags MULTIHIT;
    @SpireEnum
    public static AbstractCard.CardTags BEAM;
    @SpireEnum
    public static AbstractCard.CardTags PROTOCOL;
    @SpireEnum
    public static AbstractCard.CardTags TICK;
    @SpireEnum
    public static AbstractCard.CardTags VOLATILE;


    private ModPanel settingsPanel;
    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;


    //TODO - content sharing if needed
    /*
    public static Properties slimeboundDefault = new Properties();
    public static boolean contentSharing_relics = true;
    public static boolean contentSharing_potions = true;
    public static boolean contentSharing_events = true;
    public static boolean unlockEverything = false;

    public static final String PROP_RELIC_SHARING = "contentSharing_relics";
    public static final String PROP_POTION_SHARING = "contentSharing_potions";
    public static final String PROP_EVENT_SHARING = "contentSharing_events";
    public static final String PROP_UNLOCK_ALL = "unlockEverything";

    public static ArrayList<AbstractRelic> shareableRelics = new ArrayList<>();
    */

    public GuardianMod() {

        BaseMod.subscribe(this);
        modID = "guardianmod";

        BaseMod.addColor(AbstractCardEnum.GUARDIAN,
                GUARDIAN_COLOR, GUARDIAN_COLOR, GUARDIAN_COLOR, GUARDIAN_COLOR, GUARDIAN_COLOR, GUARDIAN_COLOR, GUARDIAN_COLOR,
                getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD),
                getResourcePath(POWER_CARD), getResourcePath(ENERGY_ORB),
                getResourcePath(ATTACK_CARD_PORTRAIT), getResourcePath(SKILL_CARD_PORTRAIT),
                getResourcePath(POWER_CARD_PORTRAIT), getResourcePath(ENERGY_ORB_PORTRAIT), getResourcePath(CARD_ENERGY_ORB));

        //TODO - Part of Settings
        /*
        slimeboundDefault.setProperty(PROP_EVENT_SHARING, "FALSE");
        slimeboundDefault.setProperty(PROP_RELIC_SHARING, "FALSE");
        slimeboundDefault.setProperty(PROP_POTION_SHARING, "FALSE");
        slimeboundDefault.setProperty(PROP_UNLOCK_ALL, "FALSE");

        loadConfigData();
        */

    }

    public static String makeBetaCardPath(String resourcePath) {
        return "guardianResources/GuardianImages/cards/joke/" + resourcePath;
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractGuardianCard) {
            ((AbstractGuardianCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }

    public static final String getResourcePath(String resource) {
        return "guardianResources/GuardianImages/" + resource;
    }

    public static final String getResourcePath() {
        return "guardianResources/GuardianImages/";
    }

    public static void initialize() {
        new GuardianMod();
    }

    public static CardGroup getSocketableCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while (var2.hasNext()) {
            AbstractCard c = (AbstractCard) var2.next();
            if (c instanceof AbstractGuardianCard) {
                AbstractGuardianCard cg = (AbstractGuardianCard) c;
                if (cg.socketCount > 0 && cg.socketCount < 5) {
                    if (cg.sockets.size() < cg.socketCount) {
                        retVal.group.add(c);
                    }
                }
            }
        }

        return retVal;
    }

    public static CardGroup getCardsWithSockets() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while (var2.hasNext()) {
            AbstractCard c = (AbstractCard) var2.next();
            if (c instanceof AbstractGuardianCard) {
                AbstractGuardianCard cg = (AbstractGuardianCard) c;
                logger.info("card name: " + c.name + " socketCount = " + cg.socketCount);
                if (cg.socketCount > 0 && cg.socketCount < 5) {
                    logger.info("card name: " + c.name);
                    retVal.group.add(c);
                }
            }
        }

        return retVal;
    }

    public static CardGroup getCardsWithFilledSockets() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractGuardianCard) {
                AbstractGuardianCard cg = (AbstractGuardianCard) c;
                if (cg.socketCount > 0) {
                    if (cg.sockets.size() > 0) {
                        retVal.group.add(c);
                    }
                }

            }
        }

        return retVal;
    }

    public static CardGroup getGemCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(GuardianMod.GEM)) {
                retVal.group.add(c);
            }
        }

        return retVal;
    }

    public static String makeID(String input) {
        String concat = "Guardian:" + input;
        return concat;
    }

    public static String printString(String s) {
        logger.info(s);
        return s;
    }

    public static int getMultihitModifiers() {
        int amount = 0;
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasPower(MultiBoostPower.POWER_ID)) {
                amount += AbstractDungeon.player.getPower(MultiBoostPower.POWER_ID).amount;
            }
        }
        return amount;
    }

    public static ArrayList<AbstractCard> getRewardGemCards(boolean onlyCommon) {
        return getRewardGemCards(onlyCommon, 3);
    }

    public static String getModID() {
        return modID;
    }


    public static ArrayList<AbstractCard> getRewardGemCards(boolean onlyCommon, int count) {
        ArrayList<String> allGemCards = new ArrayList<>();
        ArrayList<AbstractCard> rewardGemCards = new ArrayList<>();

        allGemCards.add("RED");
        allGemCards.add("GREEN");
        allGemCards.add("LIGHTBLUE");
        if (!onlyCommon) allGemCards.add("ORANGE");
        allGemCards.add("CYAN");
        if (!onlyCommon) allGemCards.add("WHITE");
        if (!onlyCommon) allGemCards.add("BLUE");
        if (!onlyCommon) allGemCards.add("CRIMSON");
        if (!onlyCommon) allGemCards.add("FRAGMENTED");
        if (!onlyCommon) allGemCards.add("PURPLE");
        if (!onlyCommon) allGemCards.add("SYNTHETIC");
        if (!UnlockTracker.isCardLocked(Gem_Yellow.ID)) {
            if (!onlyCommon) allGemCards.add("YELLOW");
        }

        int rando;
        String ID;
        for (int i = 0; i < count; i++) {
            rando = AbstractDungeon.cardRng.random(0, allGemCards.size() - 1);
            ID = allGemCards.get(rando);
            switch (ID) {
                case "RED":
                    rewardGemCards.add(new Gem_Red());
                    break;
                case "GREEN":
                    rewardGemCards.add(new Gem_Green());
                    break;
                case "LIGHTBLUE":
                    rewardGemCards.add(new Gem_Lightblue());
                    break;
                case "ORANGE":
                    rewardGemCards.add(new Gem_Orange());
                    break;
                case "CYAN":
                    rewardGemCards.add(new Gem_Cyan());
                    break;
                case "WHITE":
                    rewardGemCards.add(new Gem_White());
                    break;
                case "BLUE":
                    rewardGemCards.add(new Gem_Blue());
                    break;
                case "CRIMSON":
                    rewardGemCards.add(new Gem_Crimson());
                    break;
                case "FRAGMENTED":
                    rewardGemCards.add(new Gem_Fragmented());
                    break;
                case "PURPLE":
                    rewardGemCards.add(new Gem_Purple());
                    break;
                case "SYNTHETIC":
                    rewardGemCards.add(new Gem_Synthetic());
                    break;
                case "YELLOW":
                    rewardGemCards.add(new Gem_Yellow());
                    break;
            }
            allGemCards.remove(rando);
        }

        return rewardGemCards;
    }

    public static void updateStasisCount() {

    }

    public static int getStasisCount() {
        int count = 0;
        if (AbstractDungeon.player != null) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof StasisOrb) {
                    count++;
                }
            }
        }
        stasisCount = count;
        //logger.info(count);
        return count;
    }

    public static boolean canSpawnStasisOrb() {
        if (AbstractDungeon.player.hasEmptyOrb() || (AbstractDungeon.player.masterMaxOrbs == 0 && AbstractDungeon.player.maxOrbs == 0))
            return true;

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (!(o instanceof StasisOrb)) {
                return true;
            }
        }
        UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions");
        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 2.0F, UI_STRINGS.TEXT[5], true));

        return false;
    }

    //TODO - Part of shared relics
    /*
    public void addSharedRelics(){
        if (contentSharing_relics){
            BaseMod.addRelic(shareableRelics.get(0), RelicType.SHARED);

        } else {
            BaseMod.addRelicToCustomPool(shareableRelics.get(0), AbstractCardEnum.GUARDIAN);
        }
    }
    */

    public static boolean isStasisOrbInPlay() {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof StasisOrb) {
                return true;
            }
        }
        return false;

    }

    @Override
    public int receiveOnPlayerLoseBlock(int i) {
        if (AbstractDungeon.player.stance instanceof DefensiveMode) {
            return 0;
        } else {
            return i;
        }
    }

    public void receiveEditCharacters() {

        guardianCharacter = new GuardianCharacter("TheGuardian", GuardianEnum.GUARDIAN);
        BaseMod.addCharacter(guardianCharacter, getResourcePath("charSelect/button.png"), getResourcePath("charSelect/portrait.png"), GuardianEnum.GUARDIAN, getResourcePath("charSelect/leaderboard.png"));

    }


    @Override
    public void receiveAddAudio() {
//        BaseMod.addAudio(makeID("STANCE_LOOP_Defensive_Mode"), "guardianResources/audio/sound/sfx_defensive_mode_stance.ogg");
        BaseMod.addAudio(makeID("STANCE_LOOP_Defensive_Mode"), "guardianResources/audio/sound/sfx_defensive_mode_stance_v2.ogg");

    }

/*
    public static void clearData() {
    saveData();
}


public static void saveData() {

    try {
        SpireConfig config = new SpireConfig("GuardianMod", "SlimeboundSaveData", slimeboundDefault);
        config.setBool(PROP_EVENT_SHARING, contentSharing_events);
        config.setBool(PROP_RELIC_SHARING, contentSharing_relics);
        config.setBool(PROP_POTION_SHARING, contentSharing_potions);
        config.setBool(PROP_UNLOCK_ALL, unlockEverything);

        config.save();
    } catch (Exception e) {
        e.printStackTrace();
    }

}


    public void adjustRelics(){

        // remove all shareable relics wherever they are, then re-add them.
        // assuming right now that there are no overheated expansion relics shared by other characters.
        for (AbstractRelic relic : shareableRelics){
            BaseMod.removeRelic(relic);
            BaseMod.removeRelicFromCustomPool(relic,AbstractCardEnum.GUARDIAN);
        }

        addSharedRelics();



    }


    public static void loadConfigData() {

        try {
            logger.info("GuardianMod | Loading Config Preferences...");
            SpireConfig config = new SpireConfig("GuardianMod", "SlimeboundSaveData", slimeboundDefault);
            config.load();
            contentSharing_events = config.getBool(PROP_EVENT_SHARING);
            contentSharing_relics = config.getBool(PROP_RELIC_SHARING);
            contentSharing_potions = config.getBool(PROP_POTION_SHARING);
            unlockEverything = config.getBool(PROP_UNLOCK_ALL);
        }
        catch(Exception e) {
            e.printStackTrace();
            clearData();
        }

    }
 */

    public void receivePostDungeonInitialize() {


    }

    @Override
    public void receiveSetUnlocks() {

        downfallMod.registerUnlockSuite(
                GatlingBeam.ID,
                Orbwalk.ID,
                FierceBash.ID,

                ChargeUp.ID,
                GemFire.ID,
                GemFinder.ID,

                StasisEngine.ID,
                FuturePlans.ID,
                CompilePackage.ID,

                StasisUpgradeRelic.ID,
                DefensiveModeMoreBlock.ID,
                StasisCodex.ID,

                GemstoneGun.ID,
                PocketSentry.ID,
                BottledAnomaly.ID,

                GuardianEnum.GUARDIAN
        );
    }


    public void receiveEditRelics() {


        BaseMod.addRelicToCustomPool(new ModeShifter(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new ModeShifterPlus(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new BottledStasis(), AbstractCardEnum.GUARDIAN);
        BaseMod.registerBottleRelic(BottledStasisPatch.inBottledStasis, new BottledStasis());
        BaseMod.addRelicToCustomPool(new DefensiveModeMoreBlock(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new SackOfGems(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new StasisCodex(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new StasisSlotReductionRelic(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new StasisUpgradeRelic(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new guardian.relics.StasisEgg(), AbstractCardEnum.GUARDIAN);
        BaseMod.addRelicToCustomPool(new guardian.relics.PickAxe(), AbstractCardEnum.GUARDIAN);
        BaseMod.registerBottleRelic(BottledStasisPatch.inStasisEgg, new guardian.relics.StasisEgg());
        BaseMod.addRelic(new GemstoneGun(), RelicType.SHARED);
        BaseMod.addRelic(new PocketSentry(), RelicType.SHARED);
        BaseMod.addRelic(new BottledAnomaly(), RelicType.SHARED);
        BaseMod.registerBottleRelic(BottledStasisPatch.inBottledAnomaly, new BottledAnomaly());


    }

    public void receiveEditCards() {


        BaseMod.addDynamicVariable(new MultihitVariable());
        BaseMod.addDynamicVariable(new SecondaryMagicVariable());


        BaseMod.addCard(new Strike_Guardian());
        BaseMod.addCard(new Defend_Guardian());
        BaseMod.addCard(new ChargeUp());
        BaseMod.addCard(new CurlUp());
        BaseMod.addCard(new DecasProtection());
        BaseMod.addCard(new DonusPower());
        BaseMod.addCard(new FierceBash());
        BaseMod.addCard(new GuardianWhirl());
        BaseMod.addCard(new PolyBeam());
        BaseMod.addCard(new TwinSlam());
        BaseMod.addCard(new VentSteam());
        BaseMod.addCard(new TimeBomb());
        BaseMod.addCard(new Repulse());
        BaseMod.addCard(new HyperBeam_Guardian());
        BaseMod.addCard(new BronzeArmor());
        BaseMod.addCard(new FloatingOrbs());
        BaseMod.addCard(new OrbSlam());
        BaseMod.addCard(new SphericShield());
        BaseMod.addCard(new Harden());
        BaseMod.addCard(new SentryWave());
        BaseMod.addCard(new SentryBeam());
        BaseMod.addCard(new WalkerClaw());
        BaseMod.addCard(new Incinerate());
        BaseMod.addCard(new Orbwalk());
        BaseMod.addCard(new CompilePackage());
        BaseMod.addCard(new PackageAutomaton());
        BaseMod.addCard(new PackageDonuDeca());
        BaseMod.addCard(new PackageSentry());
        BaseMod.addCard(new PackageShapes());
        BaseMod.addCard(new PackageSphere());
        BaseMod.addCard(new PackageWalker());
        BaseMod.addCard(new PackageDefect());
        BaseMod.addCard(new Gem_Red());
        BaseMod.addCard(new Gem_Green());
        BaseMod.addCard(new Gem_Lightblue());
        BaseMod.addCard(new Gem_Cyan());
        BaseMod.addCard(new Gem_Orange());
        BaseMod.addCard(new Gem_White());
        BaseMod.addCard(new Preprogram());
        BaseMod.addCard(new Clone());
        BaseMod.addCard(new Recover());
        BaseMod.addCard(new Planning());
        BaseMod.addCard(new BodySlam());
        BaseMod.addCard(new Emergency());
        BaseMod.addCard(new GemFinder());
        BaseMod.addCard(new FastForward());
        BaseMod.addCard(new FuturePlans());
        BaseMod.addCard(new Suspension());
        BaseMod.addCard(new TimeCapacitor());
        BaseMod.addCard(new StasisField());
        BaseMod.addCard(new StasisStrike());
        BaseMod.addCard(new ConstructionForm());
        // BaseMod.addCard(new WeakpointTargeting());
        BaseMod.addCard(new GemFire());
        BaseMod.addCard(new RollAttack());
        BaseMod.addCard(new Reroute());
        BaseMod.addCard(new PrismaticBarrier());
        BaseMod.addCard(new PrismaticBeam());
        BaseMod.addCard(new ChargeCore());
        BaseMod.addCard(new ShieldSpikes());
        BaseMod.addCard(new PiercingHide());
        BaseMod.addCard(new TemporalShield());
        BaseMod.addCard(new TemporalStrike());
        BaseMod.addCard(new ExploitGems());
        BaseMod.addCard(new PrimingBeam());
        BaseMod.addCard(new BaubleBeam());
        BaseMod.addCard(new MultiBeam());
        BaseMod.addCard(new RefractedBeam());
        BaseMod.addCard(new SpikerProtocol());
        BaseMod.addCard(new ArmoredProtocol());
        BaseMod.addCard(new EvasiveProtocol());
        BaseMod.addCard(new TimeSifter());
        BaseMod.addCard(new CrystalBeam());
        BaseMod.addCard(new AncientConstruct());
        BaseMod.addCard(new Gem_Blue());
        BaseMod.addCard(new Gem_Crimson());
        BaseMod.addCard(new Gem_Fragmented());
        BaseMod.addCard(new Gem_Yellow());
        BaseMod.addCard(new Gem_Synthetic());
        BaseMod.addCard(new guardian.cards.BronzeOrb());
        BaseMod.addCard(new GatlingBeam());
        BaseMod.addCard(new RevengeProtocol());
        BaseMod.addCard(new ShieldCharger());
        BaseMod.addCard(new StasisEngine());
        BaseMod.addCard(new Gem_Purple());


        //CONSTRUCT cross-mod
        /*
        if (Loader.isModLoaded("constructmod")) {
            BaseMod.addCard(new HammerDown());
            BaseMod.addCard(new ModeShift());
            BaseMod.addCard(new OmegaCannon());
            BaseMod.addCard(new PackageConstruct());
        }
        */


        //INFINITE cross-mod
        /*
        if (Loader.isModLoaded("infinitespire")) {
            BaseMod.addCard(new MassTimeBomb());
            BaseMod.addCard(new MassOfThorns());
            BaseMod.addCard(new MassSlam());
            BaseMod.addCard(new PackageMass());
        }
        */


    }

    public void unlockEverything() {


        UnlockTracker.unlockCard(Strike_Guardian.ID);
        UnlockTracker.unlockCard(Defend_Guardian.ID);
        /*
        UnlockTracker.unlockCard(BronzeBeam.ID);
        UnlockTracker.unlockCard(LevelUp.ID);
        UnlockTracker.unlockCard(Tackle.ID);
        UnlockTracker.unlockCard(Icky.ID);
        UnlockTracker.unlockCard(SplitTorchHead.ID);
        UnlockTracker.unlockCard(SplitBruiser.ID);
        UnlockTracker.unlockCard(SplitCultist.ID);
        UnlockTracker.unlockCard(SplitLeeching.ID);
        UnlockTracker.unlockCard(SplitAcid.ID);
        UnlockTracker.unlockCard(SplitLicking.ID);
        UnlockTracker.unlockCard(ProtectTheBoss.ID);
        //UnlockTracker.unlockCard(zzzAbsorbAll.ID);
        UnlockTracker.unlockCard(Overexert.ID);
        UnlockTracker.unlockCard(Split.ID);
        UnlockTracker.unlockCard(SuperSplit.ID);
        UnlockTracker.unlockCard(LeadByExample.ID);
        UnlockTracker.unlockCard(SlimeTap.ID);
        UnlockTracker.unlockCard(RainOfGoop.ID);
        UnlockTracker.unlockCard(Teamwork.ID);
        UnlockTracker.unlockCard(SlimeBarrage.ID);
        UnlockTracker.unlockCard(SlimeBrawl.ID);
        //UnlockTracker.unlockCard(zzzMaxSlimes.ID);
        UnlockTracker.unlockCard(StudyTheSpire.ID);
        UnlockTracker.unlockCard(SelfFormingGoo.ID);
        UnlockTracker.unlockCard(SlimeSpikes.ID);
        UnlockTracker.unlockCard(GoopArmor.ID);
        UnlockTracker.unlockCard(MassRepurpose.ID);
        UnlockTracker.unlockCard(DouseInSlime.ID);
        UnlockTracker.unlockCard(Chomp.ID);
        UnlockTracker.unlockCard(BestDefense.ID);
        UnlockTracker.unlockCard(OozeBath.ID);
        UnlockTracker.unlockCard(MinionMaster.ID);
        // UnlockTracker.unlockCard(zzzSoTasty.ID);
        UnlockTracker.unlockCard(LivingWall.ID);

        UnlockTracker.unlockCard(LeechingTouch.ID);
        UnlockTracker.unlockCard(DuplicatedForm.ID);
        UnlockTracker.unlockCard(FeelOurPain.ID);
        UnlockTracker.unlockCard(Dissolve.ID);
        UnlockTracker.unlockCard(RollThrough.ID);
        UnlockTracker.unlockCard(CorrosiveSpit.ID);
        UnlockTracker.unlockCard(SamplingLick.ID);
        UnlockTracker.unlockCard(Recycling.ID);
        UnlockTracker.unlockCard(GrowthPunch.ID);
        UnlockTracker.unlockCard(Recollect.ID);
        UnlockTracker.unlockCard(FormOfPuddle.ID);
        UnlockTracker.unlockCard(Gluttony.ID);
        UnlockTracker.unlockCard(Lick.ID);
        UnlockTracker.unlockCard(MegaLick.ID);

        UnlockTracker.unlockCard(PressTheAttack.ID);
        UnlockTracker.unlockCard(SoulSicken.ID);
        // UnlockTracker.unlockCard(zzzFocusedLick.ID);
        UnlockTracker.unlockCard(HauntingLick.ID);
        UnlockTracker.unlockCard(AcidGelatin.ID);
        UnlockTracker.unlockCard(RejuvenatingLick.ID);
        UnlockTracker.unlockCard(Replication.ID);
        UnlockTracker.unlockCard(QuickStudy.ID);

        UnlockTracker.unlockCard(CheckThePlaybook.ID);
        UnlockTracker.unlockCard(FinishingTackle.ID);
        //UnlockTracker.unlockCard(zzzSlimepotheosis.ID);
        UnlockTracker.unlockCard(TongueLash.ID);
        UnlockTracker.unlockCard(PoisonLick.ID);
        UnlockTracker.unlockCard(ItLooksTasty.ID);
        UnlockTracker.unlockCard(AcidTongue.ID);
        UnlockTracker.unlockCard(TendrilStrike.ID);
        UnlockTracker.unlockCard(WasteNot.ID);
        UnlockTracker.unlockCard(HungryTackle.ID);
        UnlockTracker.unlockCard(VenomTackle.ID);
        UnlockTracker.unlockCard(GoopTackle.ID);
        UnlockTracker.unlockCard(FlameTackle.ID);
        UnlockTracker.unlockCard(GoopSpray.ID);
        UnlockTracker.unlockCard(ComboTackle.ID);
        UnlockTracker.unlockCard(Grow.ID);
        UnlockTracker.unlockCard(Prepare.ID);
        UnlockTracker.unlockCard(MassFeed.ID);
        UnlockTracker.unlockCard(ViciousTackle.ID);
        UnlockTracker.unlockCard(LeechEnergy.ID);
        UnlockTracker.unlockCard(LeechLife.ID);
        UnlockTracker.unlockCard(Equalize.ID);

        UnlockTracker.unlockCard(DisruptingSlam.ID);
        UnlockTracker.unlockCard(PrepareCrush.ID);
        UnlockTracker.unlockCard(Repurpose.ID);

        UnlockTracker.unlockCard(ServeAndProtectProtect.ID);
        UnlockTracker.unlockCard(ServeAndProtect.ID);
        UnlockTracker.unlockCard(ServeAndProtectServe.ID);
        UnlockTracker.unlockCard(DivideAndConquerDivide.ID);

        UnlockTracker.unlockCard(DivideAndConquerConquer.ID);
        UnlockTracker.unlockCard(DivideAndConquer.ID);


        //UnlockTracker.addScore(GuardianEnum.SLIMEBOUND, 1000000);

        clearUnlockBundles();

*/

    }


    public void receivePostInitialize() {

        //UIStrings configStrings = CardCrawlGame.languagePack.getUIString("slimeboundConfigMenuText");

        BaseMod.registerCustomReward(
                RewardItemTypePatch.GEM,
                (rewardSave) -> { //on load
                    GuardianMod.logger.info("gems loaded");
                    return new GemReward();
                }, (customReward) -> { //on save
                    GuardianMod.logger.info("gems saved");
                    return new RewardSave(customReward.type.toString(), null);
                });

        BaseMod.registerCustomReward(
                RewardItemTypePatch.GEMALLRARITIES,
                (rewardSave) -> { //on load
                    GuardianMod.logger.info("gems loaded");
                    return new GemRewardAllRarities();
                }, (customReward) -> { //on save
                    GuardianMod.logger.info("gems saved");
                    return new RewardSave(customReward.type.toString(), null);
                });


        //logger.info("Load Badge Image and mod options");
        // Load the Mod Badge
        //Texture badgeTexture = new Texture(getResourcePath("badge.png"));

        // Create the Mod Menu

        //settingsPanel = new ModPanel();
/*
        ModLabeledToggleButton contentSharingBtnRelics = new ModLabeledToggleButton(configStrings.TEXT[0],
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                contentSharing_relics, settingsPanel, (label) -> {}, (button) -> {
            contentSharing_relics = button.enabled;
            adjustRelics();
            saveData();
        });

        ModLabeledToggleButton contentSharingBtnEvents = new ModLabeledToggleButton(configStrings.TEXT[2],
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                contentSharing_events, settingsPanel, (label) -> {}, (button) -> {
            contentSharing_events = button.enabled;
            saveData();
        });

        ModLabeledToggleButton contentSharingBtnPotions = new ModLabeledToggleButton(configStrings.TEXT[1],
                350.0f, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                contentSharing_potions, settingsPanel, (label) -> {}, (button) -> {
            contentSharing_potions = button.enabled;
            refreshPotions();
            saveData();
        });

        ModLabeledToggleButton unlockEverythingBtn = new ModLabeledToggleButton(configStrings.TEXT[3],
                350.0f, 450.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                unlockEverything, settingsPanel, (label) -> {}, (button) -> {
            unlockEverything = button.enabled;
            unlockEverything();
            saveData();
        });



        settingsPanel.addUIElement(unlockEverythingBtn);
        settingsPanel.addUIElement(contentSharingBtnEvents);
        settingsPanel.addUIElement(contentSharingBtnPotions);
        settingsPanel.addUIElement(contentSharingBtnRelics);
*/

        //BaseMod.registerModBadge(badgeTexture, "The Guardian", "Michael Mayhem, zwolfiez", "Adds the Guardian character to the game.", settingsPanel);

        logger.info("Done loading badge Image and mod options");

        addPotions();

        BaseMod.addEvent(new AddEventParams.Builder(GemMine.ID, GemMine.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Act
                .dungeonID(Exordium.ID)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(StasisEgg.ID, StasisEgg.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Act
                .dungeonID(TheBeyond.ID)
                //Only in Evil if content sharing is disabled
                .spawnCondition(() -> (evilMode || downfallMod.contentSharing_events))
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(CrystalForge.ID, CrystalForge.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(AccursedBlacksmithGuardian.ID, AccursedBlacksmithGuardian.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Existing Event to Override//
                .overrideEvent(AccursedBlacksmith.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(PurificationShrineGuardian.ID, PurificationShrineGuardian.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Existing Event to Override//
                .overrideEvent(PurificationShrine.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(TransmogrifierGuardian.ID, TransmogrifierGuardian.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Existing Event to Override//
                .overrideEvent(Transmogrifier.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
        BaseMod.addEvent(new AddEventParams.Builder(UpgradeShrineGuardian.ID, UpgradeShrineGuardian.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Existing Event to Override//
                .overrideEvent(UpgradeShrine.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());


        BaseMod.addEvent(new AddEventParams.Builder(BackToBasicsGuardian.ID, BackToBasicsGuardian.class) //Event ID//
                //Event Character//
                .playerClass(GuardianEnum.GUARDIAN)
                //Existing Event to Override//
                .overrideEvent(BackToBasics.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        //BaseMod.addEvent(GemMine.ID, GemMine.class, Exordium.ID);
        //BaseMod.addEvent(StasisEgg.ID, StasisEgg.class, TheBeyond.ID);
        //BaseMod.addEvent(BackToBasicsSnecko.ID, BackToBasicsSnecko.class, TheCity.ID);
        //BaseMod.addEvent(CrystalForge.ID, CrystalForge.class);
        //BaseMod.addEvent(AccursedBlacksmithGuardian.ID, AccursedBlacksmithGuardian.class);
        //BaseMod.addEvent(PurificationShrineGuardian.ID, PurificationShrineGuardian.class);
        //BaseMod.addEvent(TransmogrifierGuardian.ID, TransmogrifierGuardian.class);
        //BaseMod.addEvent(UpgradeShrineGuardian.ID, UpgradeShrineGuardian.class);
        //BaseMod.addEvent(ArtOfSlimeWar.ID, ArtOfSlimeWar.class, Exordium.ID);

        initializeSocketTextures();

    }

    public void initializeSocketTextures() {
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/emptysocket.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/redgem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/greengem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/orangegem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/whitegem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/cyangem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/bluegem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/crimsongem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/fraggem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/purplegem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/blackgem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/yellowgem.png")));
        socketTextures.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/lightbluegem.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/emptysocket2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/redgem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/greengem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/orangegem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/whitegem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/cyangem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/bluegem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/crimsongem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/fraggem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/purplegem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/blackgem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/yellowgem2.png")));
        socketTextures2.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/lightbluegem2.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/emptysocket3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/redgem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/greengem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/orangegem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/whitegem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/cyangem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/bluegem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/crimsongem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/fraggem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/purplegem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/blackgem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/yellowgem3.png")));
        socketTextures3.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/lightbluegem3.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/emptysocket4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/redgem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/greengem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/orangegem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/whitegem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/cyangem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/bluegem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/crimsongem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/fraggem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/purplegem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/blackgem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/yellowgem4.png")));
        socketTextures4.add(ImageMaster.loadImage(getResourcePath("/cardIcons/templated/512/lightbluegem4.png")));

    }


    public void addPotions() {

        BaseMod.addPotion(BlockOnCardUsePotion.class, Color.ROYAL, Color.TEAL, Color.BLUE, BlockOnCardUsePotion.POTION_ID);
        BaseMod.addPotion(AcceleratePotion.class, Color.GOLDENROD, Color.GOLD, Color.YELLOW, AcceleratePotion.POTION_ID, GuardianEnum.GUARDIAN);
        BaseMod.addPotion(DefensiveModePotion.class, Color.ROYAL, Color.TEAL, Color.BLUE, DefensiveModePotion.POTION_ID, GuardianEnum.GUARDIAN);
        BaseMod.addPotion(StasisDiscoveryPotion.class, Color.GOLDENROD, Color.GOLD, Color.YELLOW, StasisDiscoveryPotion.POTION_ID, GuardianEnum.GUARDIAN);

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(BlockOnCardUsePotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(AcceleratePotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(DefensiveModePotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(StasisDiscoveryPotion.POTION_ID);
        }
    }

    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        if (!stasisDelay) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof StasisOrb) {
                    if (((StasisOrb) o).passiveAmount == 1) {
                        stasisDelay = true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        if (abstractCard.type == AbstractCard.CardType.STATUS || abstractCard.type == AbstractCard.CardType.CURSE) {
            if (AbstractDungeon.player.hasPower(ExhaustStatusesPower.POWER_ID)) {
                ExhaustStatusesPower e = (ExhaustStatusesPower) AbstractDungeon.player.getPower(ExhaustStatusesPower.POWER_ID);
                if (e.usedThisTurn < e.amount) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(abstractCard, AbstractDungeon.player.hand));
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                    e.usedThisTurn++;
                    e.flash();
                }
            }
        }
    }

    public enum socketTypes {
        RED,
        BLUE,
        GREEN,
        LIGHTBLUE,
        WHITE,
        CYAN,
        ORANGE,
        CRIMSON,
        FRAGMENTED,
        SYNTHETIC,
        PURPLE,
        YELLOW

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
            if (((GuardianCharacter) AbstractDungeon.player).inDefensive) {
                ((GuardianCharacter) AbstractDungeon.player).switchToOffensiveMode();
            }
        }
    }
}



