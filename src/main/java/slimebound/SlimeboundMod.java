package slimebound;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import downfall.cards.curses.Icky;
import downfall.downfallMod;
import expansioncontent.relics.StudyCardRelic;
import guardian.cards.*;
import guardian.events.BackToBasicsGuardian;
import guardian.patches.GuardianEnum;
import guardian.potions.AcceleratePotion;
import guardian.potions.BlockOnCardUsePotion;
import guardian.potions.DefensiveModePotion;
import guardian.potions.StasisDiscoveryPotion;
import guardian.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.actions.SlimeSpawnAction;
import slimebound.cards.*;
import slimebound.characters.SlimeboundCharacter;
import slimebound.events.*;
import slimebound.helpers.SelfDamageVariable;
import slimebound.helpers.SlimedVariable;
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;
import slimebound.patches.SlimeboundEnum;
import slimebound.potions.SlimedPotion;
import slimebound.potions.SlimyTonguePotion;
import slimebound.potions.SpawnSlimePotion;
import slimebound.potions.ThreeZeroPotion;
import slimebound.powers.AcidTonguePowerUpgraded;
import slimebound.powers.DuplicatedFormNoHealPower;
import slimebound.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import static downfall.patches.EvilModeCharacterSelect.evilMode;


@com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
public class SlimeboundMod implements OnCardUseSubscriber,
        SetUnlocksSubscriber,
        PostDungeonInitializeSubscriber, PostBattleSubscriber,
        PostInitializeSubscriber, PreMonsterTurnSubscriber,
        basemod.interfaces.EditCharactersSubscriber,
        basemod.interfaces.EditRelicsSubscriber,
        basemod.interfaces.EditCardsSubscriber,
        OnPowersModifiedSubscriber,
        //EditStringsSubscriber,
        //basemod.interfaces.PostDrawSubscriber,
        basemod.interfaces.OnStartBattleSubscriber {
    public static final boolean hasHubris;
    public static final String PROP_RELIC_SHARING = "contentSharing_relics";
    public static final String PROP_POTION_SHARING = "contentSharing_potions";
    public static final String PROP_EVENT_SHARING = "contentSharing_events";
    public static final String PROP_UNLOCK_ALL = "unlockEverything";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static final com.badlogic.gdx.graphics.Color SLIME_COLOR = com.megacrit.cardcrawl.helpers.CardHelper.getColor(25.0F, 95.0F, 25.0F);
    private static final String SLIMEBOUNDMOD_ASSETS_FOLDER = "slimeboundResources/SlimeboundImages";
    private static final String ATTACK_CARD = "512/bg_attack_slimebound.png";
    private static final String SKILL_CARD = "512/bg_skill_slimebound.png";
    private static final String POWER_CARD = "512/bg_power_slimebound.png";
    private static final String ENERGY_ORB = "512/card_slimebound_orb.png";
    private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";
    private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack_slimebound.png";
    private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill_slimebound.png";
    private static final String POWER_CARD_PORTRAIT = "1024/bg_power_slimebound.png";
    private static final String ENERGY_ORB_PORTRAIT = "1024/card_slimebound_orb.png";
    private static final String CHAR_BUTTON = "charSelect/button.png";
    private static final String CHAR_PORTRAIT = "charSelect/portrait.png";
    public static int attacksPlayedThisTurn;
    public static boolean slimeDelay;
    public static boolean huntedTriggered;
    public static boolean scrapping;
    public static SlimeboundCharacter slimeboundCharacter;
    public static boolean slimeTalked = false;
    public static boolean slimeTalkedAcidL = false;
    public static boolean slimeTalkedAcidM = false;
    public static boolean slimeTalkedAcidS = false;
    public static boolean slimeTalkedSpikeL = false;
    public static boolean slimeTalkedSpikeM = false;
    public static boolean slimeTalkedSpikeS = false;
    public static int slimeTalkedDark = 0;
    public static boolean slimeTalkedCollector = false;
    public static boolean spritealtered = false;
    public static boolean bumpnextlime = false;
    public static boolean disabledStrikeVFX = false;
    public static SpawnedSlime mostRecentSlime;
    public static boolean foughtSlimeBoss;
    public static Color placeholderColor = new Color(64F / 255F, 200F / 255F, 64F / 255F, 1);


    @SpireEnum
    public static AbstractCard.CardTags LICK;

    @SpireEnum
    public static AbstractCard.CardTags TACKLE;
    public static Properties slimeboundDefault = new Properties();
    public static boolean contentSharing_relics = true;
    public static boolean contentSharing_potions = true;
    public static boolean contentSharing_events = true;
    public static boolean unlockEverything = false;
    public static ArrayList<AbstractRelic> shareableRelics = new ArrayList<>();
    public static boolean goopGlow = false;

    private static ArrayList<String> specialistSlimes = new ArrayList<>();

    static {
        hasHubris = Loader.isModLoaded("Hubris");
        if (hasHubris) {
            logger.info("Detected Hubris");
        }
    }

    private ModPanel settingsPanel;
    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    private static String modID;

    public SlimeboundMod() {


        BaseMod.subscribe(this);
        modID = "slimeboundmod";

        BaseMod.addColor(AbstractCardEnum.SLIMEBOUND,
                SLIME_COLOR, SLIME_COLOR, SLIME_COLOR, SLIME_COLOR, SLIME_COLOR, SLIME_COLOR, SLIME_COLOR,
                getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD),
                getResourcePath(POWER_CARD), getResourcePath(ENERGY_ORB),
                getResourcePath(ATTACK_CARD_PORTRAIT), getResourcePath(SKILL_CARD_PORTRAIT),
                getResourcePath(POWER_CARD_PORTRAIT), getResourcePath(ENERGY_ORB_PORTRAIT), getResourcePath(CARD_ENERGY_ORB));

        slimeboundDefault.setProperty(PROP_EVENT_SHARING, "FALSE");
        slimeboundDefault.setProperty(PROP_RELIC_SHARING, "FALSE");
        slimeboundDefault.setProperty(PROP_POTION_SHARING, "FALSE");
        slimeboundDefault.setProperty(PROP_UNLOCK_ALL, "FALSE");

        loadConfigData();


        specialistSlimes.add("Bronze");
        specialistSlimes.add("Ghostflame");
        specialistSlimes.add("Torchhead");
        specialistSlimes.add("Cultist");
        specialistSlimes.add("Protector");
        specialistSlimes.add("Insulting");
        specialistSlimes.add("Ancient");
        specialistSlimes.add("Slowing");
    }

    public static String getResourcePath(String resource) {
        return "slimeboundResources/SlimeboundImages/" + resource;
    }

    public static void initialize() {
        new SlimeboundMod();
    }

    public static int getAcidTongueBonus(AbstractCreature source) {
        int bonus = 0;
        if (source != null) {
            if (source.hasPower(AcidTonguePowerUpgraded.POWER_ID)) {
                bonus = source.getPower(AcidTonguePowerUpgraded.POWER_ID).amount;
            }
        }
        return bonus;
    }

    public static void clearData() {
        saveData();
    }

    public static void saveData() {
        try {
            SpireConfig config = new SpireConfig("SlimeboundMod", "SlimeboundSaveData", slimeboundDefault);
            config.setBool(PROP_EVENT_SHARING, contentSharing_events);
            config.setBool(PROP_RELIC_SHARING, contentSharing_relics);
            config.setBool(PROP_POTION_SHARING, contentSharing_potions);
            config.setBool(PROP_UNLOCK_ALL, unlockEverything);

            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigData() {
        try {
            logger.info("SlimeboundMod | Loading Config Preferences...");
            SpireConfig config = new SpireConfig("SlimeboundMod", "SlimeboundSaveData", slimeboundDefault);
            config.load();
            contentSharing_events = config.getBool(PROP_EVENT_SHARING);
            contentSharing_relics = config.getBool(PROP_RELIC_SHARING);
            contentSharing_potions = config.getBool(PROP_POTION_SHARING);
            unlockEverything = config.getBool(PROP_UNLOCK_ALL);
        } catch (Exception e) {
            e.printStackTrace();
            clearData();
        }
    }

    public static void triggerGoopCardVFX() {
        goopGlow = true;
    }

    public static String getModID() {
        return modID;
    }

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static void checkForEndGoopCardVFX() {
        boolean noGoop = true;

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {
                if (monster.hasPower(Slimed.ID)) {
                    noGoop = false;
                }
            }
        }

        if (noGoop) {
            goopGlow = false;
        }

    }

    public void receiveEditCharacters() {

        slimeboundCharacter = new SlimeboundCharacter("TheSlimebound", SlimeboundEnum.SLIMEBOUND);
        BaseMod.addCharacter(slimeboundCharacter, getResourcePath("charSelect/button.png"), getResourcePath("charSelect/portrait.png"), SlimeboundEnum.SLIMEBOUND, getResourcePath("charSelect/leaderboard.png"));

    }

    public void receivePostDungeonInitialize() {

        slimeTalked = false;
        slimeTalkedAcidL = false;
        slimeTalkedAcidM = false;
        slimeTalkedAcidS = false;
        slimeTalkedSpikeL = false;
        slimeTalkedSpikeM = false;
        slimeTalkedSpikeS = false;
        slimeTalkedDark = 0;
        slimeTalkedCollector = false;
        if (AbstractDungeon.player != null) {

            SlimeboundMod.foughtSlimeBoss = false;
            ////SlimeboundMod.logger.info("Reset Hunted event bool.");

        }

    }

    public static AbstractOrb getLeadingSlime() {
        AbstractOrb oldestOrb = null;

        if (AbstractDungeon.player.maxOrbs > 0) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof SpawnedSlime) {
                    oldestOrb = o;
                }
            }
        }
        return oldestOrb;
    }

    @Override
    public void receiveSetUnlocks() {

        downfallMod.registerUnlockSuite(
                DivideAndConquer.ID,
                ServeAndProtect.ID,
                CheckThePlaybook.ID,

                SplitSpecialist.ID,
                TagTeam.ID,
                Darklings.ID,

                HungryTackle.ID,
                Recollect.ID,
                Recycling.ID,

                SlimedTailRelic.ID,
                PotencyRelic.ID,
                SlimedSkullRelic.ID,

                PreparedRelic.ID,
                StudyCardRelic.ID,
                StickyStick.ID,

                SlimeboundEnum.SLIMEBOUND
        );

    }

    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new AbsorbEndCombat(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new AbsorbEndCombatUpgraded(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new AggressiveSlimeRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new MaxSlimesRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new PotencyRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new SlimedTailRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new SlimedSkullRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new ScrapOozeRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new GreedOozeRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new SelfDamagePreventRelic(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelicToCustomPool(new TarBlob(), AbstractCardEnum.SLIMEBOUND);
        BaseMod.addRelic(new StickyStick(), RelicType.SHARED);
        BaseMod.addRelic(new PreparedRelic(), RelicType.SHARED);

    }

    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SelfDamageVariable());
        BaseMod.addDynamicVariable(new SlimedVariable());

       // BaseMod.addCard(new DivideAndConquerDivide());
       // BaseMod.addCard(new DivideAndConquerConquer());
        BaseMod.addCard(new DivideAndConquer());

      //  BaseMod.addCard(new ServeAndProtectProtect());
     //   BaseMod.addCard(new ServeAndProtectServe());
        BaseMod.addCard(new ServeAndProtect());

        BaseMod.addCard(new slimebound.cards.Defend_Slimebound());
        BaseMod.addCard(new slimebound.cards.Strike_Slimebound());
        //   BaseMod.addCard(new BronzeBeam());
        BaseMod.addCard(new LevelUp());
        BaseMod.addCard(new SplitBruiser());
        BaseMod.addCard(new SplitAcid());
        BaseMod.addCard(new SplitLeeching());
        BaseMod.addCard(new SplitLicking());
        BaseMod.addCard(new ProtectTheBoss());
        //BaseMod.addCard(new slimebound.cards.zzzAbsorbAll());
        BaseMod.addCard(new Overexert());
        BaseMod.addCard(new Split());
        //BaseMod.addCard(new SuperSplit());
        BaseMod.addCard(new LeadByExample());
        BaseMod.addCard(new Nibble());
        BaseMod.addCard(new slimebound.cards.SlimeTap());
        BaseMod.addCard(new Teamwork());
        BaseMod.addCard(new slimebound.cards.SlimeBarrage());
        BaseMod.addCard(new SlimeBrawl());
        //BaseMod.addCard(new slimebound.cards.zzzMaxSlimes());
        BaseMod.addCard(new SlimeSpikes());

        BaseMod.addCard(new GoopArmor());
        BaseMod.addCard(new MassRepurpose());
        BaseMod.addCard(new DouseInSlime());
        BaseMod.addCard(new Chomp());
        BaseMod.addCard(new BestDefense());
        BaseMod.addCard(new OozeBath());
        //BaseMod.addCard(new zzzSoTasty());
        BaseMod.addCard(new LivingWall());
        BaseMod.addCard(new MinionMaster());
        BaseMod.addCard(new SelfFormingGoo());
        BaseMod.addCard(new slimebound.cards.Dissolve());
        BaseMod.addCard(new slimebound.cards.DuplicatedForm());
        BaseMod.addCard(new slimebound.cards.LeechingTouch());
        BaseMod.addCard(new SamplingLick());
        BaseMod.addCard(new FormOfPuddle());
        BaseMod.addCard(new slimebound.cards.Lick());
        BaseMod.addCard(new slimebound.cards.MegaLick());

        BaseMod.addCard(new PressTheAttack());
        //BaseMod.addCard(new SoulSicken());
        // BaseMod.addCard(new slimebound.cards.zzzFocusedLick());
        BaseMod.addCard(new HauntingLick());
        //BaseMod.addCard(new AcidGelatin());
        BaseMod.addCard(new RejuvenatingLick());
        BaseMod.addCard(new slimebound.cards.TongueLash());
        BaseMod.addCard(new ItLooksTasty());
        BaseMod.addCard(new slimebound.cards.AcidTongue());
        //BaseMod.addCard(new slimebound.cards.TendrilStrike());
        //BaseMod.addCard(new slimebound.cards.PoisonLick());
        BaseMod.addCard(new slimebound.cards.WasteNot());
        BaseMod.addCard(new HungryTackle());
        BaseMod.addCard(new slimebound.cards.FlameTackle());
        BaseMod.addCard(new RollThrough());
        BaseMod.addCard(new ComboTackle());
        BaseMod.addCard(new GoopTackle());
        //BaseMod.addCard(new VenomTackle());
        BaseMod.addCard(new slimebound.cards.Grow());
        BaseMod.addCard(new slimebound.cards.Prepare());
        BaseMod.addCard(new slimebound.cards.Gluttony());
        //BaseMod.addCard(new automaton.cards.goodstatus.UsefulSlime());
        BaseMod.addCard(new RainOfGoop());
        BaseMod.addCard(new slimebound.cards.GoopSpray());
        BaseMod.addCard(new slimebound.cards.MassFeed());
        BaseMod.addCard(new ViciousTackle());
        BaseMod.addCard(new slimebound.cards.LeechEnergy());
        BaseMod.addCard(new LeechLife());
        BaseMod.addCard(new Equalize());

        BaseMod.addCard(new DisruptingSlam());
        BaseMod.addCard(new slimebound.cards.CorrosiveSpit());
        BaseMod.addCard(new PrepareCrush());
        BaseMod.addCard(new slimebound.cards.SlimeCrush());

        BaseMod.addCard(new slimebound.cards.Tackle());
        //BaseMod.addCard(new zzzSlimepotheosis());
        BaseMod.addCard(new slimebound.cards.FinishingTackle());
        BaseMod.addCard(new FeelOurPain());
        BaseMod.addCard(new Replication());
        BaseMod.addCard(new CheckThePlaybook());
        BaseMod.addCard(new Repurpose());
        BaseMod.addCard(new GrowthPunch());
        BaseMod.addCard(new slimebound.cards.Recycling());
        BaseMod.addCard(new slimebound.cards.Recollect());

        BaseMod.addCard(new SplitSpecialist());
        BaseMod.addCard(new Darklings());
        BaseMod.addCard(new Schlurp());
        BaseMod.addCard(new SlimeSlap());
        BaseMod.addCard(new OneTwoCombo());
        BaseMod.addCard(new ForwardTackle());
        BaseMod.addCard(new TagTeam());
        BaseMod.addCard(new RallyTheTroops());


    }

    public void receivePostBattle(AbstractRoom r) {

        goopGlow = false;

        AbstractPlayer p = AbstractDungeon.player;
        if (spritealtered) {
            AbstractDungeon.effectsQueue.add(new SmokePuffEffect(p.hb.cX, p.hb.cY));
            // AbstractDungeon.actionManager.addToBottom(new VFXAction(new DoubleSlimeParticle(AbstractDungeon.player)));
            if (p instanceof SlimeboundCharacter) {
                SlimeboundCharacter hero = (SlimeboundCharacter) p;
                hero.setRenderscale(hero.renderscale);
            }
            p.hb_x = p.hb_x - (100 * Settings.scale);
            p.drawX = p.drawX + (100 * Settings.scale);
            p.hb.cX = p.hb.cX - (100 * Settings.scale);


            spritealtered = false;
        }

        ArrayList<AbstractOrb> slimes = new ArrayList<>();

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof CultistSlime) {
                ((CultistSlime) o).cleanUpVFX();
            }
            if (o instanceof SpawnedSlime) {
                slimes.add(o);
            }
        }
        boolean soundPlayed = false;
        for (AbstractOrb slime : slimes) {
            SpawnedSlime s = (SpawnedSlime) slime;
            s.noEvokeBonus = true;
            if (soundPlayed) {
                s.noEvokeSound = true;
            } else {
                soundPlayed = true;
            }
            s.triggerEvokeAnimation();
            if (AbstractDungeon.player.hasPower(DuplicatedFormNoHealPower.POWER_ID)) {
                AbstractDungeon.player.getPower(DuplicatedFormNoHealPower.POWER_ID).onVictory();
            }

            s.noRender = true;
        }
    }

    public void receivePostInitialize() {
        addPotions();

        //Dungeon patch contains the content sharing event logic

        /*BaseMod.addEvent(Hunted.ID, Hunted.class, TheCity.ID);
        BaseMod.addEvent(Hunted.ID, Hunted.class, TheBeyond.ID);
        BaseMod.addEvent(ArtOfSlimeWar.ID, ArtOfSlimeWar.class, TheCity.ID);*/

        /*
        BaseMod.addEvent(new AddEventParams.Builder(Hunted.ID, Hunted.class) //Event ID//
                //Event Character//
                .playerClass(SlimeboundEnum.SLIMEBOUND)
                //Act//
                //Only in Evil if content sharing is disabled
                .spawnCondition(() -> (downfallMod.contentSharing_events && downfallMod.contentSharing_colorlessCards))

                .dungeonIDs(TheCity.ID, TheBeyond.ID, "TheJungle")
                //Additional Condition//
                .bonusCondition(() -> (!foughtSlimeBoss || AbstractDungeon.player.hasRelic(StudyCardRelic.ID)))
                .create());
                */

        BaseMod.addEvent(new AddEventParams.Builder(ArtOfSlimeWar.ID, ArtOfSlimeWar.class) //Event ID//
                //Act//
                .dungeonIDs(TheCity.ID, "TheJungle")
                //Only in Evil if content sharing is disabled
                .spawnCondition(() -> (evilMode || downfallMod.contentSharing_events))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(WorldOfGoopSlimebound.ID, WorldOfGoopSlimebound.class) //Event ID//
                //Event Character//
                .playerClass(SlimeboundEnum.SLIMEBOUND)
                //Existing Event to Override//
                .overrideEvent(GoopPuddle.ID)
                //Additional Condition//
                .bonusCondition(() -> !AbstractDungeon.player.hasRelic(GreedOozeRelic.ID))
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(ScrapOozeSlimebound.ID, ScrapOozeSlimebound.class) //Event ID//
                //Event Character//
                .playerClass(SlimeboundEnum.SLIMEBOUND)
                //Existing Event to Override//
                .overrideEvent(ScrapOoze.ID)
                //Additional Condition//
                .bonusCondition(() -> !AbstractDungeon.player.hasRelic(ScrapOozeRelic.ID))
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(DarklingsSlimebound.ID, DarklingsSlimebound.class) //Event ID//
                //Act//
                .dungeonIDs(TheBeyond.ID)
                //Additional Condition//
                .bonusCondition(() -> (AbstractDungeon.player instanceof SlimeboundCharacter))
                .create());



        BaseMod.addEvent(new AddEventParams.Builder(BackToBasicsSlime.ID, BackToBasicsSlime.class) //Event ID//
                //Event Character//
                .playerClass(SlimeboundEnum.SLIMEBOUND)
                //Existing Event to Override//
                .overrideEvent(BackToBasics.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        /*
        if (Loader.isModLoaded("TheJungle")){
            BaseMod.addEvent(Hunted.ID, Hunted.class, Jungle.ID);
            BaseMod.addEvent(ArtOfSlimeWar.ID, ArtOfSlimeWar.class, Jungle.ID);
        }
        */

        //BaseMod.addEvent(ArtOfSlimeWar.ID, ArtOfSlimeWar.class, Exordium.ID);


        //BaseMod.addEvent(WorldOfGoopSlimebound.ID, WorldOfGoopSlimebound.class, Exordium.ID);

    }

    public void addPotions() {

        BaseMod.addPotion(ThreeZeroPotion.class, Color.FOREST, Color.BLACK, Color.BLACK, ThreeZeroPotion.POTION_ID);
        BaseMod.addPotion(SlimedPotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, SlimedPotion.POTION_ID, SlimeboundEnum.SLIMEBOUND);
        BaseMod.addPotion(SpawnSlimePotion.class, Color.GREEN, Color.FOREST, Color.BLACK, SpawnSlimePotion.POTION_ID, SlimeboundEnum.SLIMEBOUND);
        BaseMod.addPotion(SlimyTonguePotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, SlimyTonguePotion.POTION_ID, SlimeboundEnum.SLIMEBOUND);

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(ThreeZeroPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(SlimedPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(SpawnSlimePotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(SlimyTonguePotion.POTION_ID);
        }
    }


    public void receiveCardUsed(AbstractCard c) {

        if (c.type == AbstractCard.CardType.ATTACK) {
            ++attacksPlayedThisTurn;
        }

    }


    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        slimeDelay = true;
        attacksPlayedThisTurn = 0;
        //   this.printEnemies();

        return true;
    }

    public void receiveOnBattleStart(AbstractRoom room) {
        attacksPlayedThisTurn = 0;
    }

    public static void spawnNormalSlime() {
        Integer o = AbstractDungeon.cardRng.random(0, 3);

        switch (o) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlimingSlime(), false, true));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));
                break;
        }
    }

    public static void spawnSpecialistSlime() {
        Collections.shuffle(specialistSlimes, AbstractDungeon.cardRng.random);

        switch (specialistSlimes.get(0)) {
            case "Bronze": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new BronzeSlime(), false, true));
                break;
            }
            case "Ghostflame": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new HexSlime(), false, true));
                break;
            }
            case "Torchhead": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new TorchHeadSlime(), false, true));
                break;
            }
            case "Cultist": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new CultistSlime(), false, true));
                break;
            }
            case "Protector": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ProtectorSlime(), false, true));
                break;
            }
            case "Insulting": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ChampSlime(), false, true));
                break;
            }
            case "Ancient": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new DrawingSlime(), false, true));
                break;
            }
            case "Slowing": {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlowingSlime(), false, true));
                break;
            }
        }
    }

    @Override
    public void receivePowersModified() {
        for (AbstractOrb o:AbstractDungeon.player.orbs){
            if (o instanceof SpawnedSlime){
                o.applyFocus();
            }
        }
    }
}
