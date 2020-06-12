package downfall;

/*

This package should contain all content additions strictly related to the
Evil Mode alternate gameplay run.  This includes Bosses, Events,
Event Override patches, and other things that only appear during Evil Runs.

 */

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import charbosses.actions.util.CharBossMonsterGroup;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.cards.anticards.Antidote;
import charbosses.cards.anticards.Debug;
import charbosses.cards.anticards.PeaceOut;
import charbosses.cards.anticards.ShieldSmash;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.*;
import com.megacrit.cardcrawl.events.city.*;
import com.megacrit.cardcrawl.events.exordium.*;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import com.megacrit.cardcrawl.events.shrines.*;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.GoldenIdol;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.cards.KnowingSkullWish;
import downfall.events.*;
import downfall.monsters.*;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.patches.ui.topPanel.GoldToSoulPatches;
import downfall.potions.CursedFountainPotion;
import downfall.relics.KnowingSkull;
import downfall.relics.*;
import downfall.util.ReplaceData;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.CenterGridCardSelectScreen;
import guardian.GuardianMod;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

@SpireInitializer
public class downfallMod implements
        EditStringsSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, PostUpdateSubscriber, StartGameSubscriber, StartActSubscriber {
    public static final String modID = "downfall";



    public static boolean choosingBossRelic = false;
    public static boolean choosingRemoveCard = false;
    public static boolean choosingUpgradeCard = false;
    public static boolean choosingTransformCard = false;

    public static boolean replaceMenuColor = true;
    public static boolean tempAscensionHack = false;
    public static int tempAscensionOriginalValue = 0;

    @SpireEnum
    public static AbstractCard.CardTags CHARBOSS_ATTACK;
    @SpireEnum
    public static AbstractCard.CardTags CHARBOSS_SETUP;

    public static final boolean EXPERIMENTAL_FLIP = false;
    public static Settings.GameLanguage[] SupportedLanguages = {
            // Insert other languages here
            Settings.GameLanguage.ENG,
            Settings.GameLanguage.ZHS
    };
    public static ReplaceData[] wordReplacements;
    public static SpireConfig bruhData = null;

    public downfallMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new downfallMod();
    }

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static String assetPath(String path) {
        return "downfallResources/" + path;
    }

    public static String assetPath(String path, otherPackagePaths otherPath) {
        switch (otherPath) {
            case PACKAGE_GUARDIAN:
                return "guardianResources/" + path;
            case PACKAGE_SLIME:
                return "slimeboundResources/" + path;
            case PACKAGE_SNECKO:
                return "sneckomodResources/" + path;
            case PACKAGE_HEXAGHOST:
                return "hexamodResources/" + path;
            case PACKAGE_EXPANSION:
                return "expansioncontentResources/" + path;
        }
        return "downfallResources/" + path;
    }

    public static void saveData() {
        try {
            if (bruhData == null) {
                bruhData = new SpireConfig("downfall", "TrapSaveData");
            }
            GoldenIdol_Evil.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadOtherData() {
        try {
            bruhData = new SpireConfig("downfall", "TrapSaveData");
            GoldenIdol_Evil.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeLocalizationPath(Settings.GameLanguage language, String filename) {
        String langPath = getLangString();
        return assetPath("localization/" + langPath + "/" + filename + ".json");
    }

    private String makeLocalizationPath(Settings.GameLanguage language, String filename, otherPackagePaths otherPackage) {
        String langPath = getLangString();
        return assetPath("localization/" + langPath + "/" + filename + ".json", otherPackage);
    }

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    private void loadLocalization(Settings.GameLanguage language, Class<?> stringType) {
        SlimeboundMod.logger.info("loading loc:" + language + "downfall" + stringType);
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName()));

        SlimeboundMod.logger.info("loading loc:" + language + " PACKAGE_EXPANSION" + stringType);
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName(), otherPackagePaths.PACKAGE_EXPANSION));

        SlimeboundMod.logger.info("loading loc:" + language + " PACKAGE_GUARDIAN" + stringType);
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName(), otherPackagePaths.PACKAGE_GUARDIAN));

        SlimeboundMod.logger.info("loading loc:" + language + " PACKAGE_HEXAGHOST" + stringType);
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName(), otherPackagePaths.PACKAGE_HEXAGHOST));

        SlimeboundMod.logger.info("loading loc:" + language + " PACKAGE_SLIME" + stringType);
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName(), otherPackagePaths.PACKAGE_SLIME));

        SlimeboundMod.logger.info("loading loc:" + language + " PACKAGE_SNECKO" + stringType);
        BaseMod.loadCustomStringsFile(stringType, makeLocalizationPath(language, stringType.getSimpleName(), otherPackagePaths.PACKAGE_SNECKO));
    }

    private void loadLocalization(Settings.GameLanguage language) {

        loadLocalization(language, UIStrings.class);
        loadLocalization(language, EventStrings.class);
        loadLocalization(language, RelicStrings.class);
        loadLocalization(language, MonsterStrings.class);
        loadLocalization(language, PotionStrings.class);
        loadLocalization(language, CharacterStrings.class);
        loadLocalization(language, CardStrings.class);
        //loadLocalization(language, KeywordStrings.class);
        loadLocalization(language, OrbStrings.class);
        loadLocalization(language, RunModStrings.class);
        loadLocalization(language, PowerStrings.class);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new KnowingSkullWish());
        BaseMod.addCard(new Antidote());
        BaseMod.addCard(new ShieldSmash());
        BaseMod.addCard(new Debug());
        BaseMod.addCard(new PeaceOut());
    }

    @Override
    public void receiveEditStrings() {
        loadLocalization(Settings.GameLanguage.ENG);
        if (Settings.language != Settings.GameLanguage.ENG) {
            loadLocalization(Settings.language);
        }

        try {
            String lang = getLangString();

            Gson gson = new Gson();
            String json = Gdx.files.internal(assetPath("localization/" + lang + "/replacementStrings.json")).readString(String.valueOf(StandardCharsets.UTF_8));
            wordReplacements = gson.fromJson(json, ReplaceData[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadModKeywords(String modID, otherPackagePaths otherPath) {

        String lang = getLangString();
        SlimeboundMod.logger.info("loading loc:" + lang + " " + otherPath + " keywords");

        Gson gson = new Gson();
        String json = Gdx.files.internal(assetPath("localization/" + lang + "/KeywordStrings.json", otherPath)).readString(String.valueOf(StandardCharsets.UTF_8));

        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID + "", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditKeywords() {
        loadModKeywords(HexaMod.getModID(), otherPackagePaths.PACKAGE_HEXAGHOST);
        loadModKeywords(expansionContentMod.getModID(), otherPackagePaths.PACKAGE_EXPANSION);
        loadModKeywords(SneckoMod.getModID(), otherPackagePaths.PACKAGE_SNECKO);
        loadModKeywords(SlimeboundMod.getModID(), otherPackagePaths.PACKAGE_SLIME);
        loadModKeywords(GuardianMod.getModID(), otherPackagePaths.PACKAGE_GUARDIAN);

    }

    public void receivePostInitialize() {

        loadOtherData();

        this.initializeMonsters();
        this.addPotions();
        this.initializeEvents();

    }

    private void initializeEvents() {
        BaseMod.addEvent(new AddEventParams.Builder(GremlinMatchGame_Evil.ID, GremlinMatchGame_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(GremlinMatchGame.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(GremlinWheelGame_Evil.ID, GremlinWheelGame_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(GremlinWheelGame.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        //Event only used in Gremlin Wheel relic.  Is not initialized into any Act.
        BaseMod.addEvent(new AddEventParams.Builder(GremlinWheelGame_Rest.ID, GremlinWheelGame_Rest.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> false)
                //Act//
                .dungeonID("")
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WomanInBlue_Evil.ID, WomanInBlue_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(WomanInBlue.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(LivingWall_Evil.ID, LivingWall_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(LivingWall.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Augmenter_Evil.ID, Augmenter_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(DrugDealer.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(BonfireSpirits_Evil.ID, BonfireSpirits_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Bonfire.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(GoldenShrine_Evil.ID, GoldenShrine_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(GoldShrine.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(FaceTrader_Evil.ID, FaceTrader_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(FaceTrader.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(CursedFountain.ID, CursedFountain.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(FountainOfCurseRemoval.ID)
                //Additional Condition//
                .bonusCondition(() -> AbstractDungeon.player.isCursed())
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WeMeetAgain_Evil.ID, WeMeetAgain_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(WeMeetAgain.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Designer_Evil.ID, Designer_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Designer.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(DeadGuy_Evil.ID, DeadGuy_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(DeadAdventurer.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(ShiningLight_Evil.ID, ShiningLight_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(ShiningLight.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WorldOfGoop_Evil.ID, WorldOfGoop_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(GoopPuddle.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Serpent_Evil.ID, Serpent_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Sssserpent.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WingStatue_Evil.ID, WingStatue_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(GoldenWing.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(GoldenIdol_Evil.ID, GoldenIdol_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(GoldenIdol.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Cleric_Evil.ID, Cleric_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Cleric.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(CouncilOfGhosts_Evil.ID, CouncilOfGhosts_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Ghosts.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(CursedTome_Evil.ID, CursedTome_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(CursedTome.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(ForgottenAltar_Evil.ID, ForgottenAltar_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(ForgottenAltar.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Bandits_Evil.ID, Bandits_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(MaskedBandits.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(KnowingSkull_Evil.ID, KnowingSkull_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(com.megacrit.cardcrawl.events.city.KnowingSkull.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Vagrant_Evil.ID, Vagrant_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Addict.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Mausoleum_Evil.ID, Mausoleum_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(TheMausoleum.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Beggar_Evil.ID, Beggar_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Beggar.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(TheNest_Evil.ID, TheNest_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Nest.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Colosseum_Evil.ID, Colosseum_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Colosseum.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(MindBloom_Evil.ID, MindBloom_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(MindBloom.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(MoaiHead_Evil.ID, MoaiHead_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(MoaiHead.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Nloth_Evil.ID, Nloth_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(Nloth.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(SensoryStone_Evil.ID, SensoryStone_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(SensoryStone.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Portal_Evil.ID, Portal_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(SecretPortal.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(TombRedMask_Evil.ID, TombRedMask_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(TombRedMask.ID)
                //Additional Condition//
                .bonusCondition(() -> AbstractDungeon.player.hasRelic(RedIOU.ID))
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WindingHalls_Evil.ID, WindingHalls_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(WindingHalls.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Joust_Evil.ID, Joust_Evil.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Event ID to Override//
                .overrideEvent(TheJoust.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(BossTester.ID, BossTester.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> false)
                .create());
    }

    public static ArrayList<String> possEncounterList = new ArrayList<>();

    private void initializeMonsters() {

        BaseMod.addMonster(LadyInBlue.ID, LadyInBlue::new);

        BaseMod.addMonster(Augmenter.ID, Augmenter::new);

        BaseMod.addMonster(FleeingMerchant.ID, FleeingMerchant::new);

        BaseMod.addMonster("downfall:CharBossMerchant", () -> new CharBossMonsterGroup(new AbstractMonster[]{new CharBossMerchant()}));

        BaseMod.addMonster(downfall.monsters.FaceTrader.ID, downfall.monsters.FaceTrader::new);

        BaseMod.addMonster("downfall:Heads", "Living Wall Heads", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ChangingTotem(),
                        new ForgetfulTotem(),
                        new GrowingTotem(),
                }));

        BaseMod.addMonster("downfall:LooterAlt", "Looter Evil", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new LooterAlt(0.0F, 0.0F)
                }));

        BaseMod.addMonster("downfall:LooterAlt2", "Looter Evil x2", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new LooterAlt(-200.0F, 15.0F),
                        new MuggerAlt(80.0F, 0.0F)
                }));

        BaseMod.addMonster("downfall:CharBossIronclad", () -> new CharBossMonsterGroup(new AbstractMonster[]{new CharBossIronclad()}));
        BaseMod.addMonster("downfall:CharBossSilent", () -> new CharBossMonsterGroup(new AbstractMonster[]{new CharBossSilent()}));
        BaseMod.addMonster("downfall:CharBossDefect", () -> new CharBossMonsterGroup(new AbstractMonster[]{new CharBossDefect()}));
        BaseMod.addMonster("downfall:CharBossWatcher", () -> new CharBossMonsterGroup(new AbstractMonster[]{new CharBossWatcher()}));

        BaseMod.addMonster("downfall:NeowBoss", NeowBoss::new);


    }

    public void addPotions() {

        BaseMod.addPotion(CursedFountainPotion.class, Color.PURPLE, Color.MAROON, Color.BLACK, CursedFountainPotion.POTION_ID);

    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new ShatteredFragment(), RelicType.SHARED);
        BaseMod.addRelic(new BrokenWingStatue(), RelicType.SHARED);
        BaseMod.addRelic(new CloakOfManyFaces(), RelicType.SHARED);
        BaseMod.addRelic(new GremlinSack(), RelicType.SHARED);
        BaseMod.addRelic(new GremlinWheel(), RelicType.SHARED);
        BaseMod.addRelic(new RedIOU(), RelicType.SHARED);
        BaseMod.addRelic(new RedIOUUpgrade(), RelicType.SHARED);
        BaseMod.addRelic(new KnowingSkull(), RelicType.SHARED);
        BaseMod.addRelic(new HeartBlessingBlue(), RelicType.SHARED);
        BaseMod.addRelic(new HeartBlessingGreen(), RelicType.SHARED);
        BaseMod.addRelic(new HeartBlessingRed(), RelicType.SHARED);
        BaseMod.addRelic(new TeleportStone(), RelicType.SHARED);
        BaseMod.addRelic(new HeartsMalice(), RelicType.SHARED);
    }

    @Override
    public void receivePostUpdate() {
        if (choosingBossRelic && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, RelicLibrary.getRelic(AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID));
            choosingBossRelic = false;
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (choosingUpgradeCard && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 54
            card.upgrade();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));// 59
            choosingUpgradeCard = false;
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (choosingRemoveCard && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card.untip();// 73
            card.unhover();// 74
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float) Settings.WIDTH / 2, (float) Settings.HEIGHT / 2.0F));// 75
            AbstractDungeon.player.masterDeck.removeCard(card);// 78
            choosingRemoveCard = false;
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        if (choosingTransformCard && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);// 79
            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);// 80
            AbstractCard transCard = AbstractDungeon.getTransformedCard();// 81
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(transCard, c.current_x, c.current_y));// 82
            choosingTransformCard = false;
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public void receiveStartGame() {
        if (CardCrawlGame.loadingSave) {
            GoldToSoulPatches.changeGoldToSouls(false);
        }
        if (!CardCrawlGame.loadingSave) {
            possEncounterList.clear();
            possEncounterList.add("downfall:CharBossIronclad");
            possEncounterList.add("downfall:CharBossSilent");
            possEncounterList.add("downfall:CharBossDefect");
            possEncounterList.add("downfall:CharBossWatcher");
            FleeingMerchant.DEAD = false;
            FleeingMerchant.CURRENT_HP = 500;
            FleeingMerchant.CURRENT_STRENGTH = 0;
            FleeingMerchant.CURRENT_SOULS = 0;
            Cleric_Evil.encountered = false;
            Cleric_Evil.heDead = false;
            AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(AbstractDungeon.player, false);
            AddBustKeyButtonPatches.KeyFields.bustedRuby.set(AbstractDungeon.player, false);
            AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(AbstractDungeon.player, false);
        }
    }

    @Override
    public void receiveStartAct() {
        if (evilMode) {
            if (AbstractDungeon.actNum <= 3) {
                Method setBoss = null;
                try {
                    AbstractDungeon.bossKey = possEncounterList.remove(AbstractDungeon.cardRandomRng.random(possEncounterList.size() - 1));
                    setBoss = AbstractDungeon.class.getDeclaredMethod("setBoss", String.class);
                    setBoss.setAccessible(true);
                    setBoss.invoke(CardCrawlGame.dungeon, AbstractDungeon.bossKey);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public enum otherPackagePaths {
        PACKAGE_SLIME,
        PACKAGE_GUARDIAN,
        PACKAGE_HEXAGHOST,
        PACKAGE_SNECKO,
        PACKAGE_EXPANSION;

        otherPackagePaths() {
        }
    }
}
