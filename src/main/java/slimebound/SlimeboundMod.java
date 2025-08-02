package slimebound;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import downfall.downfallMod;
import expansioncontent.relics.StudyCardRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import slimebound.cards.*;
import slimebound.cards.licktokens.*;
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
import slimebound.slimes.SlimeHelper;

import java.util.ArrayList;
import java.util.Collections;

import static downfall.patches.EvilModeCharacterSelect.evilMode;


@com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
public class SlimeboundMod implements OnCardUseSubscriber,
        SetUnlocksSubscriber,
        PostDungeonInitializeSubscriber, PostBattleSubscriber,
        PostInitializeSubscriber, PreMonsterTurnSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        OnStartBattleSubscriber,
        PostPlayerUpdateSubscriber {
    public static final boolean hasHubris;
//    public static final String PROP_RELIC_SHARING = "contentSharing_relics";
//    public static final String PROP_POTION_SHARING = "contentSharing_potions";
//    public static final String PROP_EVENT_SHARING = "contentSharing_events";
//    public static final String PROP_UNLOCK_ALL = "unlockEverything";
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
    public static AbstractCard.CardTags BURIED;

    @SpireEnum
    public static AbstractCard.CardTags TACKLE;
    public static ArrayList<AbstractRelic> shareableRelics = new ArrayList<>();
    public static boolean goopGlow = false;

    static {
        hasHubris = Loader.isModLoaded("Hubris");
        if (hasHubris) {
            logger.info("Detected Hubris");
        }
    }

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

//        slimeboundDefault.setProperty(PROP_EVENT_SHARING, "FALSE");
//        slimeboundDefault.setProperty(PROP_RELIC_SHARING, "FALSE");
//        slimeboundDefault.setProperty(PROP_POTION_SHARING, "FALSE");
//        slimeboundDefault.setProperty(PROP_UNLOCK_ALL, "FALSE");

        loadConfigData();

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
//        try {
//            SpireConfig config = new SpireConfig("SlimeboundMod", "SlimeboundSaveData", slimeboundDefault);
//            config.setBool(PROP_EVENT_SHARING, contentSharing_events);
//            config.setBool(PROP_RELIC_SHARING, contentSharing_relics);
//            config.setBool(PROP_POTION_SHARING, contentSharing_potions);
//            config.setBool(PROP_UNLOCK_ALL, unlockEverything);
//
//            config.save();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void loadConfigData() {
//        try {
//            logger.info("SlimeboundMod | Loading Config Preferences...");
//            SpireConfig config = new SpireConfig("SlimeboundMod", "SlimeboundSaveData", slimeboundDefault);
//            config.load();
//            contentSharing_events = config.getBool(PROP_EVENT_SHARING);
//            contentSharing_relics = config.getBool(PROP_RELIC_SHARING);
//            contentSharing_potions = config.getBool(PROP_POTION_SHARING);
//            unlockEverything = config.getBool(PROP_UNLOCK_ALL);
//        } catch (Exception e) {
//            e.printStackTrace();
//            clearData();
//        }
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractSlimeboundCard) {
            ((AbstractSlimeboundCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = hermit.util.TextureLoader.getTexture("slimeboundResources/SlimeboundImages/betacards/" + img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
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


    @Override
    public void receiveSetUnlocks() {

        //TODO - Review unlocks post-Cid/Pike
        downfallMod.registerUnlockSuite(
                DivideAndConquer.ID,
                ServeAndProtect.ID,
                CheckThePlaybook.ID,

                SplitSpecialist.ID,
                TagTeam.ID,
                FirmFortitude.ID,

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
        //BaseMod.addCard(new SamplingLick());
        BaseMod.addCard(new FormOfPuddle());
       // BaseMod.addCard(new slimebound.cards.Lick());
       // BaseMod.addCard(new slimebound.cards.MegaLick());

        BaseMod.addCard(new PressTheAttack());
        //BaseMod.addCard(new SoulSicken());
        // BaseMod.addCard(new slimebound.cards.zzzFocusedLick());
        //BaseMod.addCard(new HauntingLick());
        //BaseMod.addCard(new AcidGelatin());
       // BaseMod.addCard(new RejuvenatingLick());
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
        BaseMod.addCard(new FirmFortitude());
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

        BaseMod.addCard(new Conspire());
        BaseMod.addCard(new SplitAnAttack());
        BaseMod.addCard(new NewPlan());
        BaseMod.addCard(new Windup());

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


        //TODO - this may still be relevant to move to Cid when the Cultist enchantment is active
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof CultistSlime) {
                ((CultistSlime) o).cleanUpVFX();
            }

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

        BaseMod.addPotion(SlimedPotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, SlimedPotion.POTION_ID, SlimeboundEnum.SLIMEBOUND);
        BaseMod.addPotion(SpawnSlimePotion.class, Color.GREEN, Color.FOREST, Color.BLACK, SpawnSlimePotion.POTION_ID, SlimeboundEnum.SLIMEBOUND);
        BaseMod.addPotion(SlimyTonguePotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, SlimyTonguePotion.POTION_ID, SlimeboundEnum.SLIMEBOUND);
        //        BanSharedContentPatch.registerRunLockedPotion(SlimeboundEnum.SLIMEBOUND, ThreeZeroPotion.POTION_ID);

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
        //SlimeHelper.AtBattleStart();
    }

    public static AbstractCard getRandomLick(){
        int choice = AbstractDungeon.cardRng.random(0, 4);
        switch (choice) {
            case 0:
                return new LickTokenBlock();
            case 1:
                return new LickTokenDouble();
            case 2:
                return new LickTokenVuln();
            case 3:
                return new LickTokenDraw();
            case 4:
                return new LickTokenWeak();
        }
        return new LickTokenWeak(); //default never should be hit
    }


    @Override
    public void receivePostPlayerUpdate() {
        /*
        if (CardCrawlGame.chosenCharacter == SlimeboundEnum.SLIMEBOUND && SlimeHelper.InCombat()) {
            SlimeHelper.Update();
        }

         */
    }
}
