package gremlin;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;
import downfall.patches.BanSharedContentPatch;
import gremlin.cards.*;
import gremlin.cards.SharpenBlades;
import gremlin.characters.GremlinCharacter;
import gremlin.events.BackToBasicsGremlin;
import gremlin.events.GremlinTrenchcoat;
import gremlin.events.ScrapOozeGremlins;
import gremlin.orbs.*;
import gremlin.patches.AbstractCardEnum;
import gremlin.patches.BlamageVar;
import gremlin.patches.GremlinModSaveState;
import gremlin.potions.GremlinPotion;
import gremlin.potions.NecromancyPotion;
import gremlin.potions.SwapPotion;
import gremlin.potions.WizPotion;
import gremlin.powers.AbstractGremlinPower;
import gremlin.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static basemod.BaseMod.addRelic;
import static basemod.BaseMod.addRelicToCustomPool;
import static downfall.patches.EvilModeCharacterSelect.evilMode;
import static gremlin.patches.GremlinEnum.GREMLIN;


@SpireInitializer
public class GremlinMod implements EditCharactersSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        EditRelicsSubscriber, EditCardsSubscriber, OnStartBattleSubscriber, PostBattleSubscriber,
        PostInitializeSubscriber, SetUnlocksSubscriber {
    private static String modID = "gremlin";

    private static final Color GREMLIN_COLOR = CardHelper.getColor(205.0f, 92.0f, 92.0f);
    private static final String ASSETS_FOLDER = "gremlinResources/images";

    private static final String ATTACK_CARD = "512/bg_attack_gremlin.png";
    private static final String SKILL_CARD = "512/bg_skill_gremlin.png";
    private static final String POWER_CARD = "512/bg_power_gremlin.png";
    private static final String ENERGY_ORB = "512/card_gremlin_orb.png";

    private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack_gremlin.png";
    private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill_gremlin.png";
    private static final String POWER_CARD_PORTRAIT = "1024/bg_power_gremlin.png";
    private static final String ENERGY_ORB_PORTRAIT = "1024/card_gremlin_orb.png";

    private static final String CHAR_BUTTON = "charSelect/button.png";
    private static final String CHAR_PORTRAIT = "charSelect/charBG.png";

    public static String getResourcePath(String resource) {
        return ASSETS_FOLDER + "/" + resource;
    }

    public static final Logger logger = LogManager.getLogger(GremlinMod.class);

    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags FAT_GREMLIN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags MAD_GREMLIN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags SHIELD_GREMLIN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags SNEAKY_GREMLIN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags WIZARD_GREMLIN;
    @SpireEnum
    public static com.megacrit.cardcrawl.cards.AbstractCard.CardTags NOB_GREMLIN;

    public static boolean gremlinArtMode = false;

    public GremlinMod() {
        logger.info("Loading GremlinMod.");
        BaseMod.subscribe(this);

        BaseMod.addColor(AbstractCardEnum.GREMLIN,
                GREMLIN_COLOR, GREMLIN_COLOR, GREMLIN_COLOR, GREMLIN_COLOR, GREMLIN_COLOR, GREMLIN_COLOR, GREMLIN_COLOR,
                getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD), getResourcePath(POWER_CARD),
                getResourcePath(ENERGY_ORB), getResourcePath(ATTACK_CARD_PORTRAIT),
                getResourcePath(SKILL_CARD_PORTRAIT), getResourcePath(POWER_CARD_PORTRAIT),
                getResourcePath(ENERGY_ORB_PORTRAIT));

        BaseMod.addSaveField("Gremlins", new GremlinModSaveState());
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        new GremlinMod();
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Adding Gremlins.");
        BaseMod.addCharacter(new GremlinCharacter("The Gremlins"),
                getResourcePath(CHAR_BUTTON),
                getResourcePath(CHAR_PORTRAIT),
                GREMLIN);
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding Gremlin relics.");
        // Starter
        addRelicToCustomPool(new GremlinKnob(), AbstractCardEnum.GREMLIN);

        // Common
        addRelic(new SupplyScroll(), RelicType.SHARED);
        addRelicToCustomPool(new FragmentationGrenade(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new WizardHat(), AbstractCardEnum.GREMLIN);

        // Uncommon
        addRelic(new ImpeccablePecs(), RelicType.SHARED);
        addRelicToCustomPool(new MagicalMallet(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new WizardStaff(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new WoundPoker(), AbstractCardEnum.GREMLIN);

        // Rare
        addRelic(new PricklyShields(), RelicType.SHARED);
        addRelicToCustomPool(new GremlinBomb(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new GremlinGravestone(), AbstractCardEnum.GREMLIN);

        // Boss
        addRelicToCustomPool(new GremlinKnobUpgrade(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new LeaderVoucher(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new ShortStature(), AbstractCardEnum.GREMLIN);

        // Shop
        addRelicToCustomPool(new StolenMerchandise(), AbstractCardEnum.GREMLIN);
        addRelicToCustomPool(new TagTeamwork(), AbstractCardEnum.GREMLIN);
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding Gremlin Cards.");
        BaseMod.addDynamicVariable(new BlamageVar());

        //Basic
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new GremlinDance());
        BaseMod.addCard(new TagTeam());

        //Common Attacks
        BaseMod.addCard(new CatScratch());
        BaseMod.addCard(new DaggerDance());
        BaseMod.addCard(new Glimmer());
        BaseMod.addCard(new Jeer());
        BaseMod.addCard(new Pinprick());
        BaseMod.addCard(new PourSalt());
        BaseMod.addCard(new Presto());
        BaseMod.addCard(new Pretaliation());
        BaseMod.addCard(new ToeStub());
        BaseMod.addCard(new TwistTheKnife());

        //Common Skills
        BaseMod.addCard(new BubbleBarrier());
        BaseMod.addCard(new BulkUp());
        BaseMod.addCard(new Changeo());
        BaseMod.addCard(new GlitterGuard());
        BaseMod.addCard(new GremlinArms());
        BaseMod.addCard(new GremlinMeal());
        BaseMod.addCard(new Irritability());
        BaseMod.addCard(new Patsy());
        BaseMod.addCard(new Tadah());
        BaseMod.addCard(new Tricksy());

        //Uncommon Attacks
        BaseMod.addCard(new AggressiveDefense());
        BaseMod.addCard(new BurlyBlow());
        BaseMod.addCard(new CounterStrike());
        BaseMod.addCard(new Dazzle());
        BaseMod.addCard(new FanOfKnives());
        BaseMod.addCard(new FeelTheAudience());
        BaseMod.addCard(new Flurry());
        BaseMod.addCard(new FollowThrough());
        BaseMod.addCard(new GremlinOffensive());
        BaseMod.addCard(new GremlinToss());
        BaseMod.addCard(new IrksomeBlow());
        BaseMod.addCard(new Kablamo());
        BaseMod.addCard(new Pickpocket());
        BaseMod.addCard(new PinNeedle());
        BaseMod.addCard(new ProperTools());
        BaseMod.addCard(new Stupend());

        //Uncommon Skills
        BaseMod.addCard(new ArmsTheft());
        BaseMod.addCard(new Astound());
        BaseMod.addCard(new EdibleArmor());
        BaseMod.addCard(new Mockery());
        BaseMod.addCard(new PartyStick());
        BaseMod.addCard(new RageBreak());
        BaseMod.addCard(new Raid());
        BaseMod.addCard(new Revel());
        BaseMod.addCard(new Rhythm());
        BaseMod.addCard(new Scatter());
        BaseMod.addCard(new ShankStone());
        BaseMod.addCard(new SharpenBlades());
        BaseMod.addCard(new ShowOfHands());
        BaseMod.addCard(new Whiz());

        //Uncommon Powers
        BaseMod.addCard(new Enthusiasm());
        BaseMod.addCard(new Heckle());
        BaseMod.addCard(new InfiniteBlocks());
        BaseMod.addCard(new MakeshiftArmor());
        BaseMod.addCard(new Polish());
        BaseMod.addCard(new Wizardry());

        //Rare Attacks
        BaseMod.addCard(new Exacerbate());
        BaseMod.addCard(new FlipOut());
        BaseMod.addCard(new Fury());
        BaseMod.addCard(new SecondVolley());
        BaseMod.addCard(new ShowStopper());

        //Rare Skills
        BaseMod.addCard(new BrokenShin());
        BaseMod.addCard(new Duplicate());
        BaseMod.addCard(new Erupt());
        BaseMod.addCard(new FairyDust());
        BaseMod.addCard(new Necromancy());

        //Rare Powers
        BaseMod.addCard(new CongaLine());
        BaseMod.addCard(new Encore());
        BaseMod.addCard(new Nob());
        BaseMod.addCard(new ShadowShiv());
        BaseMod.addCard(new TargetWeakness());
        BaseMod.addCard(new Unforgiving());

        //Special
//        BaseMod.addCard(new Bellow());
//        BaseMod.addCard(new Rush());
//        BaseMod.addCard(new SkullBash());
        BaseMod.addCard(new Ward());

    }

    @Override
    public void receiveEditStrings() {
        String language = "eng";
        logger.info("Loading GremlinMod Strings.");
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                "gremlinResources/localization/" +language+"/CharacterStrings.json");
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "gremlinResources/localization/" +language+"/CardStrings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "gremlinResources/localization/" +language+"/PowerStrings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                "gremlinResources/localization/" +language+"/OrbStrings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "gremlinResources/localization/" +language+"/RelicStrings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                "gremlinResources/localization/" +language+"/UIStrings.json");

        // EventStrings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                "gremlinResources/localization/" +language+"/EventStrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        String language = "eng";
        final Gson gson = new Gson();
        String jsonPath = "gremlinResources/localization/" +language+"/";
        final String json = Gdx.files.internal(jsonPath + "KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        final Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (final Keyword keyword : keywords) {
                logger.info("Adding Keyword - " + keyword.PROPER_NAME + " | " + keyword.NAMES[0]);
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter)(AbstractDungeon.player)).startOfBattle();
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter)(AbstractDungeon.player)).updateMobState();
        }
    }

    public static GremlinStandby getGremlinOrb(String gremlinName) {
        int hp = AbstractDungeon.player.currentHealth;
        return getGremlinOrb(gremlinName, hp);
    }

    public static ArrayList<String> getGremlinStrings(){
        ArrayList<String> grems = new ArrayList<>();
        grems.add("angry");
        grems.add("fat");
        grems.add("shield");
        grems.add("sneak");
        grems.add("wizard");
        return grems;
    }

    public static GremlinStandby getGremlinOrb(String gremlinName, int hp){
        switch (gremlinName){
            case "angry":
                return new MadGremlin(hp);
            case "fat":
                return new FatGremlin(hp);
            case "shield":
                return new ShieldGremlin(hp);
            case "sneak":
                return new SneakyGremlin(hp);
            case "wizard":
                return new GremlinWizard(hp);
        }
        return new MadGremlin(hp);
    }

    public static void onSwap(){
        for(AbstractPower p: AbstractDungeon.player.powers){
            if(p instanceof AbstractGremlinPower){
                ((AbstractGremlinPower) p).onGremlinSwap();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof AbstractGremlinCard){
                ((AbstractGremlinCard) c).onGremlinSwap();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(c instanceof AbstractGremlinCard){
                ((AbstractGremlinCard) c).onGremlinSwapInDeck();
            }
        }
        for(AbstractCard c: AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractGremlinCard) {
                ((AbstractGremlinCard) c).onGremlinSwapInDeck();
            }
        }
        for(AbstractRelic relic: AbstractDungeon.player.relics){
            if(relic instanceof AbstractGremlinRelic){
                ((AbstractGremlinRelic) relic).onGremlinSwap();
            }
        }
    }

    public static void onGremlinDeath(){
        for(AbstractRelic relic: AbstractDungeon.player.relics){
            if(relic instanceof AbstractGremlinRelic){
                ((AbstractGremlinRelic) relic).onGremlinDeath();
            }
        }
    }

    public static boolean doesEnemyIntendToAttack(final AbstractMonster m) {
        if (m == null){
            return false;
        }
        if (m instanceof AbstractCharBoss) {
            final AbstractCharBoss boss = (AbstractCharBoss)m;
            for (AbstractCard card : boss.hand.group) {
                if (card instanceof AbstractBossCard) {
                    AbstractMonster.Intent intent = ((AbstractBossCard) card).intent;
                    if (!((AbstractBossCard) card).bossDarkened) {
                        if (intent == AbstractMonster.Intent.ATTACK
                                || intent == AbstractMonster.Intent.ATTACK_BUFF
                                || intent == AbstractMonster.Intent.ATTACK_DEBUFF
                                || intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        AbstractMonster.Intent intent = m.intent;
        return intent == AbstractMonster.Intent.ATTACK
                || intent == AbstractMonster.Intent.ATTACK_BUFF
                || intent == AbstractMonster.Intent.ATTACK_DEBUFF
                || intent == AbstractMonster.Intent.ATTACK_DEFEND;
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addPotion(SwapPotion.class, Color.BLACK, Color.GRAY, Color.SLATE, SwapPotion.POTION_ID, GREMLIN);
        BaseMod.addPotion(GremlinPotion.class, Color.RED, Color.YELLOW, Color.BLUE, GremlinPotion.POTION_ID, GREMLIN);
        BaseMod.addPotion(NecromancyPotion.class, Color.RED, Color.YELLOW, Color.BLUE, NecromancyPotion.POTION_ID, GREMLIN);
        BaseMod.addPotion(WizPotion.class, Color.PURPLE, Color.PINK, Color.PURPLE, WizPotion.POTION_ID);
        BanSharedContentPatch.registerRunLockedPotion(GREMLIN, WizPotion.POTION_ID);

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(GremlinPotion.POTION_ID);
        }

        BaseMod.addEvent(new AddEventParams.Builder(BackToBasicsGremlin.ID, BackToBasicsGremlin.class) //Event ID//
                //Event Character//
                .playerClass(GREMLIN)
                //Existing Event to Override//
                .overrideEvent(BackToBasics.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(ScrapOozeGremlins.ID, ScrapOozeGremlins.class) //Event ID//
                //Event Character//
                .playerClass(GREMLIN)
                //Existing Event to Override//
                .overrideEvent(ScrapOoze.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(GremlinTrenchcoat.ID, GremlinTrenchcoat.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode)
                //Prevent from appearing too early//
                .bonusCondition(() -> (AbstractDungeon.floorNum > 4))
                //Event Type//
                .eventType(EventUtils.EventType.NORMAL)
                .create());
    }

    @Override
    public void receiveSetUnlocks() {
        downfallMod.registerUnlockSuite(
                Enthusiasm.ID,
                PartyStick.ID,
                CongaLine.ID,

                Raid.ID,
                Revel.ID,
                Necromancy.ID,

                Unforgiving.ID,
                ShowStopper.ID,
                Nob.ID,

                GremlinGravestone.ID,
                GremlinBomb.ID,
                ShortStature.ID,

                SupplyScroll.ID,
                ImpeccablePecs.ID,
                PricklyShields.ID,

                GREMLIN
        );
    }
}