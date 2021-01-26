package champ;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import champ.actions.FatigueHpLossAction;
import champ.cards.*;
import champ.events.*;
import champ.monsters.BlackKnight;
import champ.potions.CounterstrikePotion;
import champ.potions.OpenerPotion;
import champ.potions.TechPotion;
import champ.potions.UltimateStancePotion;
import champ.powers.CounterPower;
import champ.powers.ResolvePower;
import champ.relics.*;
import champ.stances.AbstractChampStance;
import champ.util.CardFilter;
import champ.util.CoolVariable;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.downfallMod;
import downfall.util.CardIgnore;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class ChampMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        // EditStringsSubscriber,
        //EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        SetUnlocksSubscriber,
        OnCardUseSubscriber,
        PreMonsterTurnSubscriber,
        OnPlayerLoseBlockSubscriber,
        PostUpdateSubscriber {
    public static final String SHOULDER1 = "champResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "champResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "champResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "champResources/images/512/card_champ_orb.png";
    public static final String TEXT_ENERGY = "champResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "champResources/images/512/bg_attack_colorless.png";
    private static final String SKILL_S_ART = "champResources/images/512/bg_skill_colorless.png";
    private static final String POWER_S_ART = "champResources/images/512/bg_power_colorless.png";
    private static final String ATTACK_L_ART = "champResources/images/1024/bg_attack_colorless.png";
    private static final String SKILL_L_ART = "champResources/images/1024/bg_skill_colorless.png";
    private static final String POWER_L_ART = "champResources/images/1024/bg_power_colorless.png";
    private static final String CARD_ENERGY_L = "champResources/images/1024/card_champ_orb.png";
    private static final String CHARSELECT_BUTTON = "champResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "champResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(100F / 255F, 100F / 255F, 100F / 255F, 1);
    public static Color potionLabColor = new Color(200F / 255F, 200F / 255F, 200F / 255F, 1);

    @SpireEnum
    public static AbstractCard.CardTags OPENER;
    @SpireEnum
    public static AbstractCard.CardTags OPENERDEFENSIVE;
    @SpireEnum
    public static AbstractCard.CardTags OPENERBERSERKER;
    @SpireEnum
    public static AbstractCard.CardTags FINISHER;
    @SpireEnum
    public static AbstractCard.CardTags TECHNIQUE;
    @SpireEnum
    public static AbstractCard.CardTags COMBO;
    @SpireEnum
    public static AbstractCard.CardTags COMBODEFENSIVE;
    @SpireEnum
    public static AbstractCard.CardTags COMBOBERSERKER;

    private static String modID = "champ";
    public static int finishersThisTurn = 0;
    public static int finishersThisCombat = 0;
    public static int techniquesThisTurn = 0;
    public static boolean talked1 = false;
    public static boolean talked2 = false;

    public static boolean enteredDefensiveThisTurn;
    public static boolean enteredBerserkerThisTurn;
    public static boolean enteredGladiatorThisTurn;

    public static final TextureAtlas UIAtlas = new TextureAtlas();
    public static Texture heartOrb;
    public static Texture crown;
    public static Texture finisher;
    public static Texture openerAll;
    public static Texture openerR;
    public static Texture openerY;
    public static Texture openerB;
    public static Texture openerRB;
    public static Texture openerYR;
    public static Texture openerYB;

    public ChampMod() {
        BaseMod.subscribe(this);

        modID = "champ";

        BaseMod.addColor(ChampChar.Enums.CHAMP_GRAY, placeholderColor, placeholderColor, placeholderColor,
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
        ChampMod champMod = new ChampMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = ChampMod.class.getProtectionDomain().getCodeSource().getLocation();
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

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new ChampChar("The Champ", ChampChar.Enums.THE_CHAMP),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, ChampChar.Enums.THE_CHAMP);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BerserkersGuideToSlaughter(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new BlackKnightsHelmet(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new ChampionCrown(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new ChampionCrownUpgraded(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new DefensiveTrainingManual(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new FightingForDummies(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new GladiatorsBookOfMartialProwess(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new SignatureFinisher(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new PowerArmor(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new SpectersHand(), ChampChar.Enums.CHAMP_GRAY);
        BaseMod.addRelicToCustomPool(new LiftRelic(), ChampChar.Enums.CHAMP_GRAY);

        BaseMod.addRelic(new Barbells(), RelicType.SHARED);
        BaseMod.addRelic(new DeflectingBracers(), RelicType.SHARED);
        BaseMod.addRelic(new DuelingGlove(), RelicType.SHARED);
        BaseMod.addRelic(new ChampStancesModRelic(), RelicType.SHARED);

    }


    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new CoolVariable());
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void atb(AbstractGameAction q) {
        AbstractDungeon.actionManager.addToBottom(q);
    }


    public void addPotions() {

        BaseMod.addPotion(CounterstrikePotion.class, Color.GRAY, Color.GRAY, Color.BLACK, CounterstrikePotion.POTION_ID);
        BaseMod.addPotion(OpenerPotion.class, Color.TEAL, Color.GREEN, Color.FOREST, OpenerPotion.POTION_ID, ChampChar.Enums.THE_CHAMP);
        BaseMod.addPotion(TechPotion.class, Color.BLUE, Color.PURPLE, Color.MAROON, TechPotion.POTION_ID, ChampChar.Enums.THE_CHAMP);
        BaseMod.addPotion(UltimateStancePotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, UltimateStancePotion.POTION_ID, ChampChar.Enums.THE_CHAMP);

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(CounterstrikePotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(OpenerPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(TechPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(UltimateStancePotion.POTION_ID);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        finishersThisTurn = 0;
        finishersThisCombat = 0;
        techniquesThisTurn = 0;
        StanceHelper.init();
        enteredBerserkerThisTurn = false;
        enteredDefensiveThisTurn = false;
        enteredGladiatorThisTurn = false;
    }

    @Override
    public void receiveSetUnlocks() {

        downfallMod.registerUnlockSuite(
                BerserkerStyle.ID,
                ViciousMockery.ID,
                DefensiveStyle.ID,

                RageSigil.ID,
                ShieldSigil.ID,
                SwordSigil.ID,

                EnchantShield.ID,
                EnchantSword.ID,
                EnchantCrown.ID,

                SignatureFinisher.ID,
                PowerArmor.ID,
                SpectersHand.ID,

                DuelingGlove.ID,
                Barbells.ID,
                DeflectingBracers.ID,

                ChampChar.Enums.THE_CHAMP
        );

    }


    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof ChampChar) {
            if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
                AbstractDungeon.player.stance = new NeutralStance();
                ChampChar c = (ChampChar) AbstractDungeon.player;
                c.switchStanceVisual(NeutralStance.STANCE_ID);
            }
        }
        finishersThisTurn = 0;
        finishersThisCombat = 0;
        techniquesThisTurn = 0;
    }


    public void receivePostInitialize() {
        addPotions();

        BaseMod.addMonster("champ:BlackKnight", BlackKnight.NAME, () -> new MonsterGroup(
                new AbstractMonster[]{
                        new BlackKnight()
                }));

        heartOrb = TextureLoader.getTexture("champResources/images/heartOrb.png");
        crown = TextureLoader.getTexture("champResources/images/cardicons/crown.png");
        finisher = TextureLoader.getTexture("champResources/images/cardicons/finisher.png");
        openerAll = TextureLoader.getTexture("champResources/images/cardicons/openerall.png");
        openerB = TextureLoader.getTexture("champResources/images/cardicons/openerblue.png");
        openerR = TextureLoader.getTexture("champResources/images/cardicons/openerred.png");
        openerY = TextureLoader.getTexture("champResources/images/cardicons/openeryellow.png");
        openerYR = TextureLoader.getTexture("champResources/images/cardicons/openeryellowred.png");
        openerYB = TextureLoader.getTexture("champResources/images/cardicons/openerblueyellow.png");
        openerRB = TextureLoader.getTexture("champResources/images/cardicons/openerredblue.png");

        UIAtlas.addRegion("heartOrb", heartOrb, 0, 0, heartOrb.getWidth(), heartOrb.getHeight());
        UIAtlas.addRegion("openerAll", openerAll, 0, 0, openerAll.getWidth(), openerAll.getHeight());
        UIAtlas.addRegion("crown", crown, 0, 0, crown.getWidth(), crown.getHeight());
        UIAtlas.addRegion("finisher", finisher, 0, 0, finisher.getWidth(), finisher.getHeight());
        UIAtlas.addRegion("openerB", openerB, 0, 0, openerB.getWidth(), openerB.getHeight());
        UIAtlas.addRegion("openerR", openerR, 0, 0, openerR.getWidth(), openerR.getHeight());
        UIAtlas.addRegion("openerY", openerY, 0, 0, openerY.getWidth(), openerY.getHeight());
        UIAtlas.addRegion("openerYR", openerYR, 0, 0, openerYR.getWidth(), openerYR.getHeight());
        UIAtlas.addRegion("openerYB", openerYB, 0, 0, openerYB.getWidth(), openerYB.getHeight());
        UIAtlas.addRegion("openerRB", openerRB, 0, 0, openerRB.getWidth(), openerRB.getHeight());

        BaseMod.addEvent(new AddEventParams.Builder(Colosseum_Evil_Champ.ID, Colosseum_Evil_Champ.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> evilMode && AbstractDungeon.player instanceof ChampChar)
                //Event ID to Override//
                .overrideEvent(Colosseum.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Library_Champ.ID, Library_Champ.class) //Event ID//
                //Event Spawn Condition//
                .spawnCondition(() -> AbstractDungeon.player instanceof ChampChar)
                //Event ID to Override//
                .overrideEvent(TheLibrary.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(TechniqueTome.ID, TechniqueTome.class) //Event ID//
                //Event Character//
                .playerClass(ChampChar.Enums.THE_CHAMP)
                .eventType(EventUtils.EventType.SHRINE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(Gym.ID, Gym.class) //Event ID//
                //Event Character//
                .playerClass(ChampChar.Enums.THE_CHAMP)
                .dungeonID(Exordium.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(MinorLeagueArena.ID, MinorLeagueArena.class) //Event ID//
                //Event Spawn Condition//

                .playerClass(ChampChar.Enums.THE_CHAMP)
                .dungeonID(Exordium.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());
                /*
                .spawnCondition(() -> (evilMode || downfallMod.contentSharing_events))
                .dungeonID(Exordium.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());
                */


        BaseMod.addEvent(new AddEventParams.Builder(BackToBasicsChamp.ID, BackToBasicsChamp.class) //Event ID//
                //Event Character//
                .playerClass(ChampChar.Enums.THE_CHAMP)
                //Existing Event to Override//
                .overrideEvent(BackToBasics.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());


    }

    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        finishersThisTurn = 0;
        techniquesThisTurn = 0;
        enteredBerserkerThisTurn = false;
        enteredDefensiveThisTurn = false;
        enteredGladiatorThisTurn = false;
        return true;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {

        TipHelperChamp2.rememberedCard = null;
        TipHelperChamp3.rememberedCard = null;
        /*
        if (abstractCard.hasTag(ChampMod.OPENER)) {
            atb(new OpenerReduceCostAction());
        }
        if (abstractCard.hasTag(ChampMod.FINISHER)) {
            finishersThisTurn++;
            if (AbstractDungeon.player.stance instanceof GladiatorStance) {
                if (finishersThisTurn > 1) {
                    atb(new com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction());
                }
            } else {
                atb(new com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction());
            }
        }
        */
    }

    @Override
    public int receiveOnPlayerLoseBlock(int i) {
        if (AbstractDungeon.player.hasRelic(DeflectingBracers.ID)) {
            AbstractDungeon.player.getRelic(DeflectingBracers.ID).flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(i), i));
        }
        return i;
    }

    public static boolean endTurnIncoming = false;

    @Override
    public void receivePostUpdate() {
        if (endTurnIncoming && AbstractDungeon.actionManager.cardQueue.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
            endTurnIncoming = false;
        }
    }

    public static void updateTechniquesInCombat() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(TECHNIQUE)) c.initializeDescription();
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(TECHNIQUE)) c.initializeDescription();
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(TECHNIQUE)) c.initializeDescription();
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.hasTag(TECHNIQUE)) c.initializeDescription();
        }
        for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            if (c.hasTag(TECHNIQUE)) c.initializeDescription();
        }
    }

    public static int fatigue(int begone) {

        int y = AbstractDungeon.player.currentHealth;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = Math.min(begone, AbstractDungeon.player.currentHealth - 1);
                if (AbstractDungeon.player.hasRelic(PowerArmor.ID) && AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                    if (x + AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount > PowerArmor.CAP_RESOLVE_ETC) {
                        x = PowerArmor.CAP_RESOLVE_ETC - AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                    }
                }
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(x), x));
                AbstractDungeon.actionManager.addToTop(new FatigueHpLossAction(AbstractDungeon.player, AbstractDungeon.player, x));
            }
        });

        /*atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (y - AbstractDungeon.player.currentHealth > 0) {
                    att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(y - AbstractDungeon.player.currentHealth), y - AbstractDungeon.player.currentHealth));
                }
            }
        });
        */ //This unused method makes it so the player only gains Resolve equal to lost HP. Fixes some breakable things, but also unfun.

        return Math.min(begone, AbstractDungeon.player.currentHealth - 1);
    }
}
