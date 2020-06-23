package theHexaghost;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;
import downfall.downfallMod;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import sneckomod.relics.UnknownEgg;
import theHexaghost.events.CouncilOfGhosts_Hexa;
import theHexaghost.events.HexaFalling;
import theHexaghost.events.SealChamber;
import theHexaghost.events.WanderingSpecter;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.potions.BurningPotion;
import theHexaghost.potions.DoubleChargePotion;
import theHexaghost.potions.EctoCoolerPotion;
import theHexaghost.potions.InfernoChargePotion;
import theHexaghost.relics.*;
import theHexaghost.util.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static downfall.patches.EvilModeCharacterSelect.evilMode;
import static theHexaghost.GhostflameHelper.*;
import static theHexaghost.TheHexaghost.oscillarator;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class HexaMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        // EditStringsSubscriber,
        //EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        SetUnlocksSubscriber,
        PreRoomRenderSubscriber,
        PostDeathSubscriber {
    public static final String SHOULDER1 = "hexamodResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "hexamodResources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = "hexamodResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "hexamodResources/images/512/card_hexaghost_orb.png";
    public static final String TEXT_ENERGY = "hexamodResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "hexamodResources/images/512/bg_attack_hexaghost.png";
    private static final String SKILL_S_ART = "hexamodResources/images/512/bg_skill_hexaghost.png";
    private static final String POWER_S_ART = "hexamodResources/images/512/bg_power_hexaghost.png";
    private static final String ATTACK_L_ART = "hexamodResources/images/1024/bg_attack_hexaghost.png";
    private static final String SKILL_L_ART = "hexamodResources/images/1024/bg_skill_hexaghost.png";
    private static final String POWER_L_ART = "hexamodResources/images/1024/bg_power_hexaghost.png";
    private static final String CARD_ENERGY_L = "hexamodResources/images/1024/card_hexaghost_orb.png";
    private static final String CHARSELECT_BUTTON = "hexamodResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "hexamodResources/images/charSelect/charBG.png";
    public static boolean renderFlames = false;
    public static boolean unsealed = false;
    public static Color placeholderColor = new Color(114F / 255F, 62F / 255F, 109F / 255F, 1);
    private static String modID;

    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    public HexaMod() {
        BaseMod.subscribe(this);

        modID = "hexamod";

        BaseMod.addColor(TheHexaghost.Enums.GHOST_GREEN, placeholderColor, placeholderColor, placeholderColor,
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
        HexaMod hexaMod = new HexaMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = HexaMod.class.getProtectionDomain().getCodeSource().getLocation();
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
            if (cls.hasAnnotation(CardNoSeen.class)) {
                UnlockTracker.hardUnlockOverride(card.cardID);
            }
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheHexaghost("the Hexaghost", TheHexaghost.Enums.THE_SPIRIT),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheHexaghost.Enums.THE_SPIRIT, HexaMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new SpiritBrand(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new InflammatoryLetter(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new IceCube(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new JarOfFuel(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new MatchstickCase(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new RecyclingMachine(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new SoulConsumer(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new SoulOfChaos(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new TheBrokenSeal(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new CandleOfCauterizing(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new Sixitude(), TheHexaghost.Enums.GHOST_GREEN);

//        BaseMod.addRelic(new CandleOfCauterizing(), RelicType.SHARED);
//        BaseMod.addRelic(new Sixitude(), RelicType.SHARED);
        BaseMod.addRelic(new BolsterEngine(), RelicType.SHARED);
    }


    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new BurnVariable());
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

        BaseMod.addPotion(EctoCoolerPotion.class, Color.GRAY, Color.GRAY, Color.BLACK, EctoCoolerPotion.POTION_ID);
        BaseMod.addPotion(BurningPotion.class, Color.TEAL, Color.GREEN, Color.FOREST, BurningPotion.POTION_ID, TheHexaghost.Enums.THE_SPIRIT);
        BaseMod.addPotion(DoubleChargePotion.class, Color.BLUE, Color.PURPLE, Color.MAROON, DoubleChargePotion.POTION_ID, TheHexaghost.Enums.THE_SPIRIT);
        BaseMod.addPotion(InfernoChargePotion.class, Color.PURPLE, Color.PURPLE, Color.MAROON, InfernoChargePotion.POTION_ID, TheHexaghost.Enums.THE_SPIRIT);

    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        GhostflameHelper.init();
        if (AbstractDungeon.player instanceof TheHexaghost) {
            renderFlames = true;
            if (AbstractDungeon.scene instanceof TheBottomScene) {
                ArrayList<InteractableTorchEffect> torches = (ArrayList<InteractableTorchEffect>) ReflectionHacks.getPrivate(AbstractDungeon.scene, TheBottomScene.class, "torches");
                torches.clear();
            }
        }
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

        BaseMod.addUnlockBundle(unlocks0, TheHexaghost.Enums.THE_SPIRIT, 0);

        BaseMod.addUnlockBundle(unlocks1, TheHexaghost.Enums.THE_SPIRIT, 1);

        BaseMod.addUnlockBundle(unlocks2, TheHexaghost.Enums.THE_SPIRIT, 2);

        BaseMod.addUnlockBundle(unlocks3, TheHexaghost.Enums.THE_SPIRIT, 3);

        BaseMod.addUnlockBundle(unlocks4, TheHexaghost.Enums.THE_SPIRIT, 4);
*/
    }


    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        renderFlames = false;
    }

    public void receivePreRoomRender(SpriteBatch sb) {
        if (renderFlames) {
            GhostflameHelper.render(sb);
        }
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && renderFlames) {
            if (activeGhostFlame != null) activeGhostFlame.renderGhostflameTriggerUI(sb);
            oscillarator();
            sb.setColor(oscillarator());
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                if (activeGhostFlame == gf || showAll) {
                    sb.setColor(partialTransparent);
                    float x = 0;
                    float y = 0;
                    switch (hexaGhostFlames.indexOf(gf)) {
                        case 0:
                            x = AbstractDungeon.player.drawX - (115.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (440.0F * Settings.scale);
                            break;
                        case 1:
                            x = AbstractDungeon.player.drawX + (180.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (440.0F * Settings.scale);
                            break;
                        case 2:
                            x = AbstractDungeon.player.drawX + (240.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (270.0F * Settings.scale);
                            break;
                        case 3:
                            x = AbstractDungeon.player.drawX + (170.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (100.0F * Settings.scale);
                            break;
                        case 4:
                            x = AbstractDungeon.player.drawX - (115.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (100.0F * Settings.scale);
                            break;
                        case 5:
                            x = AbstractDungeon.player.drawX - (200.0F * Settings.scale);
                            y = AbstractDungeon.player.drawY + (270.0F * Settings.scale);
                            break;
                    }
                    /*
                    if (showAll) {
                        Texture b = gf.getHelperTexture();
                        sb.draw(b, x - (10 * Settings.scale), y - (10 * Settings.scale), 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);
                    }
                    */

                    float flashP = (gf.flashTimer - 1F) / .5F;
                    //SlimeboundMod.logger.info(gf.flashTimer + "," + flashP);
                    float fontScale = Settings.scale * 0.8F;
                    if (flashP > 0F) {
                        if (flashP > .5F) {
                            if (gf.charged) {
                                sb.setColor(Interpolation.exp5Out.apply(0F, 1F, flashP), 1F, Interpolation.exp5Out.apply(0F, 1F, flashP), 1F);
                                fontScale = Interpolation.linear.apply(Settings.scale * 2.5F, Settings.scale * 0.8F, flashP);
                            } else {
                                sb.setColor(1F, 1F, 1F, 1F);
                                fontScale = Interpolation.pow2Out.apply(Settings.scale * 3F, Settings.scale * 0.8F, flashP);

                            }
                        } else {
                            if (gf.charged) {
                                sb.setColor(Interpolation.exp5Out.apply(1F, 0F, flashP), 1F, Interpolation.exp5Out.apply(1F, 0F, flashP), 1F);

                                fontScale = Interpolation.linear.apply(Settings.scale * 0.8F, Settings.scale * 2.5F, flashP);
                            } else {
                                sb.setColor(1F, 1F, 1F, 1F);
                                fontScale = Interpolation.exp5In.apply(Settings.scale * 0.8F, Settings.scale * 3F, flashP);
                            }
                        }
                    } else {
                        sb.setColor(1F, 1F, 1F, 1F);
                    }

                    Texture b = gf.getHelperEffectTexture();
                    sb.setColor(1F, 1F, 1F, 0.6F);
                    sb.draw(b, x - (gf.effectIconXOffset * Settings.scale), y + gf.effectIconYOffset * Settings.scale, 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);

                    sb.setColor(1F, 1F, 1F, 1F);
                    FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, gf.returnHoverHelperText(), x, y, gf.textColor, fontScale);// 150 153

                    if (gf instanceof BolsteringGhostflame) {
                        sb.setColor(1F, 1F, 1F, 0.6F);
                        Texture c = TextureLoader.getTexture(HexaMod.makeUIPath("strength.png"));
                        sb.draw(c, x - (gf.effectIconXOffset * Settings.scale), y + (gf.effectIconYOffset * Settings.scale) - (50F * Settings.scale), 0, 0, b.getWidth(), b.getHeight(), Settings.scale, Settings.scale, 0, 0, 0, b.getWidth(), b.getHeight(), false, false);
                        sb.setColor(1F, 1F, 1F, 1F);
                        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "1", x, y - 50F * Settings.scale, gf.textColor, fontScale);// 150 153

                    }
                }
            }
        }
    }

    @Override
    public void receivePostDeath() {
        renderFlames = false;
    }

    public void receivePostInitialize() {
        addPotions();

        BaseMod.addEvent(new AddEventParams.Builder(WanderingSpecter.ID, WanderingSpecter.class) //Event ID//
                //Extra Requirement
                .bonusCondition(HexaMod::canGetCurseRelic)
                //Only in Evil if content sharing is disabled
                .spawnCondition(() -> (evilMode || downfallMod.contentSharing_events))
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(SealChamber.ID, SealChamber.class) //Event ID//
                //Event Character//
                .playerClass(TheHexaghost.Enums.THE_SPIRIT)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(HexaFalling.ID, HexaFalling.class) //Event ID//
                //Event Character//
                .playerClass(TheHexaghost.Enums.THE_SPIRIT)
                //Existing Event to Override//
                .overrideEvent(Falling.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(CouncilOfGhosts_Hexa.ID, CouncilOfGhosts_Hexa.class) //Event ID//
                //Event Character//
                .playerClass(TheHexaghost.Enums.THE_SPIRIT)
                //Existing Event to Override//
                .overrideEvent(Ghosts.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
    }

    public static boolean canGetCurseRelic() {
        ArrayList<String> possRelicsList = new ArrayList<>();
        possRelicsList.add(BlueCandle.ID);
        possRelicsList.add(DarkstonePeriapt.ID);
        possRelicsList.add(DuVuDoll.ID);

        for (AbstractRelic q : AbstractDungeon.player.relics) {
            possRelicsList.removeIf(q.relicId::equals);
        }

        return possRelicsList.isEmpty();
    }
}
