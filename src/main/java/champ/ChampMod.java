package champ;

import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import champ.relics.ChampionCrown;
import champ.util.CardFilter;
import champ.util.CardIgnore;
import champ.util.CoolVariable;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

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
        PreMonsterTurnSubscriber {
    public static final String SHOULDER1 = "champResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "champResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "champResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "champResources/images/512/card_hexaghost_orb.png";
    public static final String TEXT_ENERGY = "champResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "champResources/images/512/bg_attack_hexaghost.png";
    private static final String SKILL_S_ART = "champResources/images/512/bg_skill_hexaghost.png";
    private static final String POWER_S_ART = "champResources/images/512/bg_power_hexaghost.png";
    private static final String ATTACK_L_ART = "champResources/images/1024/bg_attack_hexaghost.png";
    private static final String SKILL_L_ART = "champResources/images/1024/bg_skill_hexaghost.png";
    private static final String POWER_L_ART = "champResources/images/1024/bg_power_hexaghost.png";
    private static final String CARD_ENERGY_L = "champResources/images/1024/card_hexaghost_orb.png";
    private static final String CHARSELECT_BUTTON = "champResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "champResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(100F / 255F, 100F / 255F, 100F / 255F, 1);
    /*
    @SpireEnum
    public static AbstractCard.CardTags OPENER;
    @SpireEnum
    public static AbstractCard.CardTags FINISHER;
    @SpireEnum
    public static AbstractCard.CardTags DEFENSIVE_TECH;
    @SpireEnum
    public static AbstractCard.CardTags GLADIATOR_TECH;
    @SpireEnum
    public static AbstractCard.CardTags BERSERKER_TECH;
    @SpireEnum
    public static AbstractCard.CardTags TECHNIQUE;
    */
    private static String modID = "champ";
    public int finishersPlayedThisTurn = 0;
    public static int techniquesThisTurn = 0;
    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    public static final TextureAtlas UIAtlas = new TextureAtlas();
    public static Texture heartOrb;

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
        BaseMod.addRelicToCustomPool(new ChampionCrown(), ChampChar.Enums.CHAMP_GRAY);
        /*
        BaseMod.addRelicToCustomPool(new SpiritBrand(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new InflammatoryLetter(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new IceCube(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new JarOfFuel(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new MatchstickCase(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new RecyclingMachine(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new SoulConsumer(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new SoulOfChaos(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new TheBrokenSeal(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new CandleOfCauterizing(), ChampChar.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new Sixitude(), ChampChar.Enums.GHOST_GREEN);
        */

//        BaseMod.addRelic(new CandleOfCauterizing(), RelicType.SHARED);
//        BaseMod.addRelic(new Sixitude(), RelicType.SHARED);
        //BaseMod.addRelic(new BolsterEngine(), RelicType.SHARED);
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
/*
        BaseMod.addPotion(EctoCoolerPotion.class, Color.GRAY, Color.GRAY, Color.BLACK, EctoCoolerPotion.POTION_ID);
        BaseMod.addPotion(BurningPotion.class, Color.TEAL, Color.GREEN, Color.FOREST, BurningPotion.POTION_ID, ChampChar.Enums.THE_CHAMP);
        BaseMod.addPotion(DoubleChargePotion.class, Color.BLUE, Color.PURPLE, Color.MAROON, DoubleChargePotion.POTION_ID, ChampChar.Enums.THE_CHAMP);
        BaseMod.addPotion(InfernoChargePotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, InfernoChargePotion.POTION_ID, ChampChar.Enums.THE_CHAMP);
*/
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }

    @Override
    public void receiveSetUnlocks() {
/*
        unlocks0 = new CustomUnlockBundle(
                GiftsFromTheDead.ID, PowerFromBeyond.ID, FlamesFromBeyond.ID
        );
        UnlockTracker.addCard(GiftsFromTheDead.ID);
        UnlockTracker.addCard(PowerFromBeyond.ID);
        UnlockTracker.addCard(FlamesFromBeyond.ID);

        unlocks1 = new CustomUnlockBundle(
                Toasty.ID, SpectralSpark.ID, SuperheatedStrike.ID
        );
        UnlockTracker.addCard(Toasty.ID);
        UnlockTracker.addCard(SpectralSpark.ID);
        UnlockTracker.addCard(SuperheatedStrike.ID);

        unlocks2 = new CustomUnlockBundle(
                ApocalypticArmor.ID, ApocalypseNow.ID, UnlimitedPower.ID
        );
        UnlockTracker.addCard(ApocalypticArmor.ID);
        UnlockTracker.addCard(ApocalypseNow.ID);
        UnlockTracker.addCard(UnlimitedPower.ID);

        unlocks3 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                RecyclingMachine.ID, SoulOfChaos.ID, JarOfFuel.ID
        );
        UnlockTracker.addRelic(RecyclingMachine.ID);
        UnlockTracker.addRelic(SoulOfChaos.ID);
        UnlockTracker.addRelic(JarOfFuel.ID);

        unlocks4 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                BolsterEngine.ID, CandleOfCauterizing.ID, Sixitude.ID
        );
        UnlockTracker.addRelic(BolsterEngine.ID);
        UnlockTracker.addRelic(CandleOfCauterizing.ID);
        UnlockTracker.addRelic(Sixitude.ID);

        BaseMod.addUnlockBundle(unlocks0, ChampChar.Enums.THE_CHAMP, 0);

        BaseMod.addUnlockBundle(unlocks1, ChampChar.Enums.THE_CHAMP, 1);

        BaseMod.addUnlockBundle(unlocks2, ChampChar.Enums.THE_CHAMP, 2);

        BaseMod.addUnlockBundle(unlocks3, ChampChar.Enums.THE_CHAMP, 3);

        BaseMod.addUnlockBundle(unlocks4, ChampChar.Enums.THE_CHAMP, 4);
*/
    }


    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        finishersPlayedThisTurn = 0;
    }


    public void receivePostInitialize() {
        addPotions();

        heartOrb = TextureLoader.getTexture("champResources/images/heartOrb.png");
        UIAtlas.addRegion("heartOrb", heartOrb, 0, 0, heartOrb.getWidth(), heartOrb.getHeight());

        /*
        BaseMod.addEvent(new AddEventParams.Builder(WanderingSpecter.ID, WanderingSpecter.class) //Event ID//
                //Extra Requirement
                .bonusCondition(ChampMod::canGetCurseRelic)
                //Only in Evil if content sharing is disabled
                .spawnCondition(() -> (evilMode || downfallMod.contentSharing_events))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(SealChamber.ID, SealChamber.class) //Event ID//
                //Event Character//
                .playerClass(ChampChar.Enums.THE_CHAMP)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(HexaFalling.ID, HexaFalling.class) //Event ID//
                //Event Character//
                .playerClass(ChampChar.Enums.THE_CHAMP)
                //Existing Event to Override//
                .overrideEvent(Falling.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(CouncilOfGhosts_Hexa.ID, CouncilOfGhosts_Hexa.class) //Event ID//
                //Event Character//
                .playerClass(ChampChar.Enums.THE_CHAMP)
                //Existing Event to Override//
                .overrideEvent(Ghosts.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
                */
    }

    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        finishersPlayedThisTurn = 0;
        return false;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {

        /*
        if (abstractCard.hasTag(ChampMod.OPENER)) {
            atb(new OpenerReduceCostAction());
        }
        if (abstractCard.hasTag(ChampMod.FINISHER)) {
            finishersPlayedThisTurn++;
            if (AbstractDungeon.player.stance instanceof GladiatorStance) {
                if (finishersPlayedThisTurn > 1) {
                    atb(new com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction());
                }
            } else {
                atb(new com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction());
            }
        }
        */
    }

}
