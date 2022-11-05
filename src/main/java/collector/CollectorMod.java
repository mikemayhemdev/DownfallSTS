package collector;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import collector.actions.OrderAction;
import collector.cards.AbstractCollectorCard;
import collector.patches.CollectibleCardColorEnumPatch;
import collector.patches.ExtraDeckButtonPatches.TopPanelExtraDeck;
import collector.patches.TorchHeadPatches.MonsterIntentPatch;
import collector.patches.TorchHeadPatches.MonsterPowerPatch;
import collector.patches.TorchHeadPatches.MonsterTargetPatch;
import collector.patches.TorchHeadPatches.StanceChangeParticlePatch;
import collector.powers.SufferingPower;
import collector.relics.EmeraldTorch;
import collector.util.TargetMarker;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;

import java.util.ArrayList;
import java.util.HashMap;
import static collector.util.Wiz.*;

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
        StartGameSubscriber,
        OnPowersModifiedSubscriber {
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
    public static ArrayList<String> Afflictions = new ArrayList<>();
    public static ArrayList<String> Boons = new ArrayList<>();
    public static HashMap<String, AbstractCard> cardsList;
    public static Color COLLECTIBLE_CARD_COLOR = CardHelper.getColor(13, 158, 153);
    public static int TorchAggro = 0;
    public static TargetMarker targetMarker;

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

    public static boolean isAfflicted (AbstractMonster m){
        return (m.hasPower(VulnerablePower.POWER_ID) && m.hasPower(WeakPower.POWER_ID));
    }

    public static void applySuffering (AbstractMonster m, int count){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new SufferingPower(m, count)));
    }

    public static void order (){
        atb(new OrderAction());}


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
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO: Set this up

    }

    public void receivePostInitialize() {
        addPotions();
        targetMarker = new TargetMarker();
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
    public void receivePowersModified() {
        if (AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.THE_COLLECTOR)
        ) {
            ArrayList<PowerTip> tips = new ArrayList<>();
            TorchChar d = CollectorChar.torch;
            if (d.isDead) {
                tips.add(new PowerTip(TorchChar.characterStrings.NAMES[0], TorchChar.characterStrings.TEXT[0]));
            } else {
                for (AbstractPower p : d.powers) {
                    if (p.region48 != null) {
                        tips.add(new PowerTip(p.name, p.description, p.region48));
                    } else {
                        tips.add(new PowerTip(p.name, p.description, p.img));
                    }
                }
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleStart();
        StanceChangeParticlePatch.ParticleGeneratorRenderPatch.prevPlayer = null;
        StanceChangeParticlePatch.WrathParticleRenderPatch.prevPlayer = null;
        MonsterIntentPatch.prevPlayer = null;
        MonsterPowerPatch.prevPlayer = null;
        MonsterTargetPatch.prevPlayer = null;
    }

    public static CardGroup getRareCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE) {
                retVal.group.add(c);
            }
        }

        return retVal;
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(AbstractCollectorCard.class)
                .setDefaultSeen(true)
                .cards();
    }
}