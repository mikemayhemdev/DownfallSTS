package downfall.patches;

import automaton.AutomatonChar;
import automaton.potions.BurnAndBuffPotion;
import automaton.relics.BronzeIdol;
import automaton.relics.DecasWashers;
import automaton.relics.DonusWashers;
import awakenedOne.AwakenedOneChar;
import awakenedOne.relics.*;
import champ.ChampChar;
import champ.potions.CounterstrikePotion;
import champ.relics.Barbells;
import champ.relics.DeflectingBracers;
import champ.relics.DuelingGlove;
import collector.CollectorChar;
import collector.cards.WhirlingFlame;
import collector.relics.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import downfall.cards.curses.*;
import downfall.downfallMod;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.cards.*;
import expansioncontent.potions.BossPotion;
import expansioncontent.relics.StudyCardRelic;
import gremlin.characters.GremlinCharacter;
import gremlin.potions.WizPotion;
import gremlin.relics.PricklyShields;
import gremlin.relics.SupplyScroll;
import guardian.characters.GuardianCharacter;
import guardian.potions.BlockOnCardUsePotion;
import guardian.relics.BottledAnomaly;
import guardian.relics.GemstoneGun;
import guardian.relics.PocketSentry;
import hermit.characters.hermit;
import hermit.relics.BloodyTooth;
import hermit.relics.BrassTacks;
import hermit.relics.RyeStalk;
import slimebound.characters.SlimeboundCharacter;
import slimebound.potions.ThreeZeroPotion;
import slimebound.relics.PreparedRelic;
import slimebound.relics.StickyStick;
import sneckomod.TheSnecko;
import sneckomod.potions.MuddlingPotion;
import sneckomod.relics.BlankCard;
import sneckomod.relics.SneckoTalon;
import sneckomod.relics.SuperSneckoEye;
import theHexaghost.TheHexaghost;
import theHexaghost.relics.BolsterEngine;
import theHexaghost.relics.Sixitude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanSharedContentPatch {
    private static Map<AbstractPlayer.PlayerClass, List<String>> runLockedPotions = new HashMap<>();

    public static void registerRunLockedPotion(AbstractPlayer.PlayerClass playerClass, String potionId) {
        runLockedPotions.computeIfAbsent(playerClass, _ignore -> new ArrayList<>()).add(potionId);
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class CardPatch {
        public static void Postfix(AbstractDungeon __instance) {
            if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_colorlessCards) {
                AbstractDungeon.colorlessCardPool.removeCard(AwakenDeath.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Chronoboost.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DashGenerateEvil.ID);
                AbstractDungeon.colorlessCardPool.removeCard(GuardianWhirl.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Hexaburn.ID);
                AbstractDungeon.colorlessCardPool.removeCard(HyperBeam.ID);
                AbstractDungeon.colorlessCardPool.removeCard(LastStand.ID);
                AbstractDungeon.colorlessCardPool.removeCard(ShapePower.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperPrepareCrush.ID);
                AbstractDungeon.colorlessCardPool.removeCard(QuickStudy.ID);
                AbstractDungeon.colorlessCardPool.removeCard(StudyTheSpire.ID);
                AbstractDungeon.colorlessCardPool.removeCard(YouAreMine.ID);
                AbstractDungeon.colorlessCardPool.removeCard(InvincibleStrength.ID);

                AbstractDungeon.colorlessCardPool.removeCard(BeatOfDeath.ID);
                AbstractDungeon.colorlessCardPool.removeCard(BloodBarrage.ID);
                AbstractDungeon.colorlessCardPool.removeCard(ChargeUp.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DoubleAct.ID);
                AbstractDungeon.colorlessCardPool.removeCard(FaceSlap.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Flail.ID);
                AbstractDungeon.colorlessCardPool.removeCard(GoopSpray.ID);
                AbstractDungeon.colorlessCardPool.removeCard(ManipulateTime.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Pandemonium.ID);
                AbstractDungeon.colorlessCardPool.removeCard(PeekPages.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperBloodthirst.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperBodyCrash.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperClobber.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperEtherStep.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperGhostShield.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperLivingWall.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperSomberShield.ID);
                AbstractDungeon.colorlessCardPool.removeCard(TakeFlight.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Virus.ID);

                AbstractDungeon.srcColorlessCardPool.removeCard(AwakenDeath.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Chronoboost.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(DashGenerateEvil.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(GuardianWhirl.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Hexaburn.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(HyperBeam.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(LastStand.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(ShapePower.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperPrepareCrush.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(QuickStudy.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(StudyTheSpire.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(YouAreMine.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(InvincibleStrength.ID);

                AbstractDungeon.srcColorlessCardPool.removeCard(BeatOfDeath.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(BloodBarrage.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(ChargeUp.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(DoubleAct.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(FaceSlap.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Flail.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(GoopSpray.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(ManipulateTime.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Pandemonium.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(PeekPages.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperBloodthirst.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperBodyCrash.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperClobber.ID);

                AbstractDungeon.srcColorlessCardPool.removeCard(SuperEtherStep.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperGhostShield.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperLivingWall.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperSomberShield.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(TakeFlight.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Virus.ID);

                AbstractDungeon.curseCardPool.removeCard(Aged.ID);
                AbstractDungeon.curseCardPool.removeCard(Icky.ID);
                AbstractDungeon.curseCardPool.removeCard(Bewildered.ID);
                AbstractDungeon.curseCardPool.removeCard(Haunted.ID);
                AbstractDungeon.curseCardPool.removeCard(PrideStandard.ID);
                AbstractDungeon.curseCardPool.removeCard(Malfunctioning.ID);
                AbstractDungeon.curseCardPool.removeCard(Scatterbrained.ID);
                AbstractDungeon.curseCardPool.removeCard(Sapped.ID);

                AbstractDungeon.srcCurseCardPool.removeCard(Aged.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(Icky.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(Bewildered.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(Haunted.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(PrideStandard.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(Malfunctioning.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(Scatterbrained.ID);
                AbstractDungeon.srcCurseCardPool.removeCard(Sapped.ID);
            }

            if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                AbstractDungeon.colorlessCardPool.removeCard(SuperPrepareCrush.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperPrepareCrush.ID);

                AbstractDungeon.colorlessCardPool.removeCard(GoopSpray.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(GoopSpray.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperLivingWall.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperLivingWall.ID);
            }
            if (AbstractDungeon.player instanceof TheHexaghost || RandomCardWithTagAction.hexaLocked()) {
                AbstractDungeon.colorlessCardPool.removeCard(Hexaburn.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Hexaburn.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperGhostShield.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperGhostShield.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperEtherStep.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperEtherStep.ID);
            }
            if (AbstractDungeon.player instanceof GuardianCharacter || RandomCardWithTagAction.guardianLocked()) {
                AbstractDungeon.colorlessCardPool.removeCard(GuardianWhirl.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(GuardianWhirl.ID);

                AbstractDungeon.colorlessCardPool.removeCard(ChargeUp.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(ChargeUp.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperBodyCrash.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperBodyCrash.ID);
            }
            if (AbstractDungeon.player instanceof ChampChar || RandomCardWithTagAction.champLocked()) {
                AbstractDungeon.colorlessCardPool.removeCard(LastStand.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(LastStand.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperClobber.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperClobber.ID);

                AbstractDungeon.colorlessCardPool.removeCard(FaceSlap.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(FaceSlap.ID);
            }
            if (AbstractDungeon.player instanceof AutomatonChar || RandomCardWithTagAction.autoLocked()) {
                AbstractDungeon.colorlessCardPool.removeCard(HyperBeam.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(HyperBeam.ID);

                AbstractDungeon.colorlessCardPool.removeCard(Virus.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Virus.ID);

                AbstractDungeon.colorlessCardPool.removeCard(Flail.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(Flail.ID);
            }
            if (AbstractDungeon.player instanceof CollectorChar || RandomCardWithTagAction.collectorLocked()) {
                AbstractDungeon.colorlessCardPool.removeCard(YouAreMine.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(YouAreMine.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperSomberShield.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(SuperSomberShield.ID);

                AbstractDungeon.colorlessCardPool.removeCard(SuperWhirlingFlame.ID);
                AbstractDungeon.srcColorlessCardPool.removeCard(WhirlingFlame.ID);
            }
        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeRelicList"
    )
    public static class RelicPatch {

        public static void Prefix(AbstractDungeon __instance) {
            if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_relics) {
                //last checked during awakened one dev, 5/14/25

                if (!(AbstractDungeon.player instanceof CollectorChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(AutoCurser.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(Bagpipes.ID);
                }

                if (!(AbstractDungeon.player instanceof ChampChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(Barbells.ID);
                }

                if (!(AbstractDungeon.player instanceof GuardianCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(BottledAnomaly.ID);
                }

                if (!(AbstractDungeon.player instanceof TheSnecko)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(BlankCard.ID);
                }

                if (!(AbstractDungeon.player instanceof TheHexaghost)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(BolsterEngine.ID);
                }

                if (!(AbstractDungeon.player instanceof AutomatonChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(BronzeIdol.ID);
                }

                if (!(AbstractDungeon.player instanceof hermit)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(BrassTacks.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(BloodyTooth.ID);
                }
//                AbstractDungeon.relicsToRemoveOnStart.add(CandleOfCauterizing.ID); // red candle changed to hexa specific

                if (!(AbstractDungeon.player instanceof ChampChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(DeflectingBracers.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(DuelingGlove.ID);
                }

                if (!(AbstractDungeon.player instanceof AutomatonChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(DecasWashers.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(DonusWashers.ID);
                }

                if (!(AbstractDungeon.player instanceof CollectorChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(ForbiddenFruit.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(FuelCanister.ID);
                }

                if (!(AbstractDungeon.player instanceof GuardianCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(GemstoneGun.ID);
                }

                //AbstractDungeon.relicsToRemoveOnStart.add(ImpeccablePecs.ID); impeccable pecs moved to gremlins exclusive
                if (!(AbstractDungeon.player instanceof CollectorChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(Incense.ID);
                }

                //AbstractDungeon.relicsToRemoveOnStart.add(MakeshiftBattery.ID); makeshift battery moved to automaton exclusive
                if (!(AbstractDungeon.player instanceof GuardianCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(PocketSentry.ID);
                }

                if (!(AbstractDungeon.player instanceof GremlinCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(PricklyShields.ID);
                }

                if (!(AbstractDungeon.player instanceof SlimeboundCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(PreparedRelic.ID);
                }

                if (!(AbstractDungeon.player instanceof CollectorChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(RoughDiamond.ID);
                }

                if (!(AbstractDungeon.player instanceof hermit)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(RyeStalk.ID);
                }

                if (!(AbstractDungeon.player instanceof SlimeboundCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(StickyStick.ID);
                }

                if (!(AbstractDungeon.player instanceof TheSnecko)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(StudyCardRelic.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(SuperSneckoEye.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(SneckoTalon.ID);
                }

                if (!(AbstractDungeon.player instanceof TheHexaghost)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(Sixitude.ID);
                }

                if (!(AbstractDungeon.player instanceof GremlinCharacter)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(SupplyScroll.ID);
                }

                if (!(AbstractDungeon.player instanceof AwakenedOneChar)) {
                    AbstractDungeon.relicsToRemoveOnStart.add(CawingCask.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(VioletPlumage.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(ShardOfNowak.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(MiniBlackHole.ID);
                    AbstractDungeon.relicsToRemoveOnStart.add(DeadBird.ID);
                }

            }
        }
    }

    @SpirePatch(
            clz = PotionHelper.class,
            method = "initialize"
    )

    public static class PotionPatch {
        public static void Postfix(AbstractPlayer.PlayerClass chosenClass) {
            // edit: this patch doesn't work somehow, the function is moved to downfallMod.receivePostDungeonInitialize()
            // by checking the condition before adding them all together
            //todo: I'm thinking of just making all these potions character exclusive which fixes the compendium problem
            // and people don't like these potions anyways
            if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_potions) {
                //  PotionHelper.potions.remove(SoulburnPotion.POTION_ID);
                PotionHelper.potions.remove(MuddlingPotion.POTION_ID);
                PotionHelper.potions.remove(ThreeZeroPotion.POTION_ID);
                PotionHelper.potions.remove(BlockOnCardUsePotion.POTION_ID);
                PotionHelper.potions.remove(CounterstrikePotion.POTION_ID);
                PotionHelper.potions.remove(BurnAndBuffPotion.POTION_ID);
                PotionHelper.potions.remove(WizPotion.POTION_ID);
                PotionHelper.potions.remove(BossPotion.POTION_ID);
                //   PotionHelper.potions.remove(TempHPPotion.POTION_ID);
            }
            // edit: below probably not functioning too but lazy to implement
            // Ban shared potions from other classes if you haven't played as that class before
            //  runLockedPotions.forEach((playerClass, potionIds) -> {
            // Shared potions will never be banned from their base class
            //   if (chosenClass != playerClass) {
            //   if (!HeartEvent.hasPlayedRun(playerClass)) {
            //       PotionHelper.potions.removeAll(potionIds);
            //    }
            //  });
            //     }
        }
    }
}
