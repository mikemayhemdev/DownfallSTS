package collector;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import collector.actions.OrderAction;
import collector.cards.AbstractCollectorCard;
import collector.patches.CollectibleCardColorEnumPatch;
import collector.patches.ExtraDeckButtonPatches.TopPanelExtraDeck;
import collector.powers.SufferingPower;
import collector.relics.EmeraldTorch;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;

import static collector.util.Wiz.atb;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class CollectorMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        SetUnlocksSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        StartGameSubscriber {
    public static final String SHOULDER1 = "collectorResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "collectorResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "collectorResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "collectorResources/images/512/card_collector_orb.png";
    public static final String TEXT_ENERGY = "collectorResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "collectorResources/images/512/bg_attack_.png";
    private static final String SKILL_S_ART = "collectorResources/images/512/bg_skill_.png";
    private static final String POWER_S_ART = "collectorResources/images/512/bg_power_.png";
    private static final String ATTACK_L_ART = "collectorResources/images/1024/bg_attack_.png";
    private static final String SKILL_L_ART = "collectorResources/images/1024/bg_skill_.png";
    private static final String POWER_L_ART = "collectorResources/images/1024/bg_power_.png";
    private static final String CARD_ENERGY_L = "collectorResources/images/1024/card_collector_orb.png";
    private static final String CHARSELECT_BUTTON = "collectorResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "collectorResources/images/charSelect/charBG.png";

    public static Color characterColor = CardHelper.getColor(13, 158, 131);
    public static Color potionLabColor = CardHelper.getColor(113, 158, 131);
    private static String modID = "collector";
    private CustomUnlockBundle unlocks0; // TODO: Set this up
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;
    public static Color COLLECTIBLE_CARD_COLOR = CardHelper.getColor(13, 158, 153);

    public CollectorMod() {
        BaseMod.subscribe(this);

        modID = "collector";

        BaseMod.addColor(downfallMod.Enums.COLLECTOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE,
                COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR,
                "collectorResources/images/512/bg_attack_colorless.png", "collectorResources/images/512/bg_skill_colorless.png",
                "collectorResources/images/512/bg_power_colorless.png", CARD_ENERGY_S,
                "collectorResources/images/1024/bg_attack_colorless.png", "collectorResources/images/1024/bg_skill_colorless.png",
                "collectorResources/images/1024/bg_power_colorless.png", CARD_ENERGY_L, TEXT_ENERGY);
    }




    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
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
        return downfallMod.collectorModID;
    }

    public static void initialize() {
        CollectorMod collectorMod = new CollectorMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new CollectorChar("The Collector", downfallMod.Enums.THE_COLLECTOR), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, downfallMod.Enums.THE_COLLECTOR);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new EmeraldTorch(), downfallMod.Enums.COLLECTOR);
    }

    public void addPotions() {
        //TODO: Set this up
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO: Set this up
    }

    public void receivePostInitialize() {
        addPotions();
        BaseMod.addTopPanelItem(new TopPanelExtraDeck());
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleEnd();
    }

    @Override
    public void receiveStartGame() {
        CollectorCollection.init();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleStart();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(AbstractCollectorCard.class)
                .setDefaultSeen(true)
                .cards();
    }
}