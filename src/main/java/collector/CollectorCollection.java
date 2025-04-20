package collector;

import basemod.helpers.CardModifierManager;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.monsters.*;
import collector.cardmods.CollectedCardMod;
import collector.cards.AbstractCollectorCard;
import collector.cards.collectibles.*;
import collector.patches.CollectorBottleField;
import collector.util.CollectibleCardReward;
import collector.util.EssenceReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Buffer;
import com.megacrit.cardcrawl.cards.blue.CoreSurge;
import com.megacrit.cardcrawl.cards.blue.Seek;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.cards.colorless.TheBomb;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.ConjureBlade;
import com.megacrit.cardcrawl.cards.purple.Omniscience;
import com.megacrit.cardcrawl.cards.purple.Ragnarok;
import com.megacrit.cardcrawl.cards.purple.TalkToTheHand;
import com.megacrit.cardcrawl.cards.red.Barricade;
import com.megacrit.cardcrawl.cards.red.Immolate;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.cards.red.Reaper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.monsters.ending.SpireShield;
import com.megacrit.cardcrawl.monsters.ending.SpireSpear;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.monsters.*;
import downfall.monsters.gauntletbosses.*;
import expansioncontent.cards.*;
import hermit.cards.Adapt;
import hermit.cards.FromBeyond;
import hermit.cards.Magnum;
import hermit.cards.ShadowCloak;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectorCollection {
    public static CardGroup collection;
    public static CardGroup combatCollection;
    public static HashMap<String, String> collectionPool;

    private static ArrayList<AbstractMonster> collectedAlready = new ArrayList<>();

    static {
        collectionPool = new HashMap<>();
        collectionPool.put(Cultist.ID, CultistCard.ID);
        collectionPool.put(LouseNormal.ID, LouseCard.ID);
        collectionPool.put(LouseDefensive.ID, LouseCard.ID);
        collectionPool.put(AcidSlime_L.ID, AcidSlimeCard.ID);
        collectionPool.put(AcidSlime_M.ID, AcidSlimeCard.ID);
        collectionPool.put(AcidSlime_S.ID, AcidSlimeCard.ID);
        collectionPool.put(SpikeSlime_L.ID, SpikeSlimeCard.ID);
        collectionPool.put(SpikeSlime_M.ID, SpikeSlimeCard.ID);
        collectionPool.put(SpikeSlime_S.ID, SpikeSlimeCard.ID);
        collectionPool.put(GremlinThief.ID, SneakyGremlinCard.ID);
        collectionPool.put(GremlinTsundere.ID, ShieldGremlinCard.ID);
        collectionPool.put(GremlinWizard.ID, GremlinWizardCard.ID);
        collectionPool.put(GremlinFat.ID, FatGremlinCard.ID);
        collectionPool.put(GremlinWarrior.ID, MadGremlinCard.ID);
        collectionPool.put(JawWorm.ID, JawWormCard.ID);
        collectionPool.put(Looter.ID, ThievesCard.ID);
        collectionPool.put(LooterAlt.ID, ThievesCard.ID);
        collectionPool.put(Mugger.ID, ThievesCard.ID);
        collectionPool.put(MuggerAlt.ID, ThievesCard.ID);
        collectionPool.put(FungiBeast.ID, FungiBeastCard.ID);
        collectionPool.put(ShelledParasite.ID, ShelledParasiteCard.ID);
        collectionPool.put(SphericGuardian.ID, SphericGuardianCard.ID);
        collectionPool.put(Byrd.ID, ByrdCard.ID);
        collectionPool.put(Chosen.ID, ChosenCard.ID);
        collectionPool.put(SnakePlant.ID, SnakePlantCard.ID);
        collectionPool.put(Snecko.ID, SneckoCard.ID);
        collectionPool.put(Centurion.ID, CenturionCard.ID);
        collectionPool.put(Healer.ID, MysticCard.ID);
        collectionPool.put(Darkling.ID, DarklingsCard.ID);
        collectionPool.put(OrbWalker.ID, OrbWalkerCard.ID);
        collectionPool.put(Spiker.ID, SpikerCard.ID);
        collectionPool.put(Repulsor.ID, RepulsorCard.ID);
        collectionPool.put(Exploder.ID, TheBomb.ID);
        collectionPool.put(Maw.ID, MawCard.ID);
        collectionPool.put(WrithingMass.ID, WrithingMassCard.ID);
        collectionPool.put(SpireGrowth.ID, SpireGrowthCard.ID);
        collectionPool.put(Sentry.ID, SentryCard.ID);
        collectionPool.put(GremlinNob.ID, GremlinNobCard.ID);
        collectionPool.put(Lagavulin.ID, LagavulinCard.ID);
        collectionPool.put(BookOfStabbing.ID, BookOfStabbingCard.ID);
        collectionPool.put(GremlinLeader.ID, GremlinLeaderCard.ID);
        collectionPool.put(SlaverBlue.ID, BlueSlaverCard.ID);
        collectionPool.put(SlaverRed.ID, RedSlaverCard.ID);
        collectionPool.put(Taskmaster.ID, TaskmasterCard.ID);
        collectionPool.put(BanditLeader.ID, RomeoCard.ID);
        collectionPool.put(BanditPointy.ID, PointyCard.ID);
        collectionPool.put(BanditBear.ID, BearCard.ID);
        collectionPool.put(GiantHead.ID, GiantHeadCardStageOne.ID);
        collectionPool.put(Nemesis.ID, NemesisCard.ID);
        collectionPool.put(SnakeDagger.ID, DaggerCard.ID);
        collectionPool.put(Transient.ID, TransientCard.ID);
        collectionPool.put(Reptomancer.ID, ReptomancerCard.ID);
        collectionPool.put(BronzeOrb.ID, BronzeOrbCard.ID);
        collectionPool.put(BronzeOrbWhoReallyLikesDefectForSomeReason.ID, BronzeOrbCard.ID);
        collectionPool.put(TorchHead.ID, TorchHeadCard.ID);
        collectionPool.put(MushroomPurple.ID, MushroomCard.ID);
        collectionPool.put(MushroomWhite.ID, MushroomCard.ID);
        collectionPool.put(MushroomRed.ID, MushroomCard.ID);
        collectionPool.put(FaceTrader.ID, FaceTraderCard.ID);
        collectionPool.put(ChangingTotem.ID, LivingWallCard.ID);
        collectionPool.put(ForgetfulTotem.ID, LivingWallCard.ID);
        collectionPool.put(GrowingTotem.ID, LivingWallCard.ID);
        collectionPool.put(Augmenter.ID, AugmenterCard.ID);
        collectionPool.put(FleeingMerchant.ID, MerchantCard.ID);
        collectionPool.put(CharBossMerchant.ID, MerchantCard.ID);
        collectionPool.put(Ironclad.ID, Inflame.ID);
        collectionPool.put(Silent.ID, Footwork.ID);
        collectionPool.put(Defect.ID, WhiteNoise.ID);
        collectionPool.put(Watcher.ID, TalkToTheHand.ID);
        collectionPool.put(Hermit.ID, ShadowCloak.ID);
        collectionPool.put(MirrorImageSilent.ID, Doppelganger.ID);

        collectionPool.put(Fortification.ID, SpireShieldCard.ID);
        collectionPool.put(LadyInBlue.ID, WomanInBlueCard.ID);
        collectionPool.put(SpireShield.ID, SpireShieldCard.ID);
        collectionPool.put(SpireSpear.ID, SpireSpearCard.ID);
        collectionPool.put(NeowBossFinal.ID, FinalBossCard.ID);
        collectionPool.put(CorruptHeart.ID, FinalBossCard.ID);
        collectionPool.put(TheCollector.ID, CollectorCard.ID);

        collectionPool.put(SlimeBoss.ID, SuperPrepareCrush.ID);
        collectionPool.put(TimeEater.ID, Chronoboost.ID);
        collectionPool.put(AwakenedOne.ID, AwakenDeath.ID);
        collectionPool.put(TheGuardian.ID, GuardianWhirl.ID);
        collectionPool.put(Hexaghost.ID, Hexaburn.ID);
        collectionPool.put(BronzeAutomaton.ID, HyperBeam.ID);
        collectionPool.put(Champ.ID, LastStand.ID);
        collectionPool.put(Donu.ID, ShapePower.ID);
        collectionPool.put(Deca.ID, ShapePower.ID);
    }

    public static AbstractCard getCollectedCard(AbstractMonster m) {
        AbstractCard returnValue;
        if (collectionPool.containsKey(m.id)) {
            returnValue = CardLibrary.getCopy(collectionPool.get(m.id));
        } else {
            if (m instanceof CharBossIronclad && ((CharBossIronclad) m).chosenArchetype != null) {
                switch (((CharBossIronclad) m).chosenArchetype.actNum) {
                    case 1:
                        returnValue = CardLibrary.getCopy(Immolate.ID);
                        break;
                    case 2:
                        returnValue = CardLibrary.getCopy(Reaper.ID);
                        break;
                    case 3:
                    default:
                        returnValue = CardLibrary.getCopy(Barricade.ID);
                        break;
                }
            } else if (m instanceof CharBossSilent && ((CharBossSilent) m).chosenArchetype != null) {
                switch (((CharBossSilent) m).chosenArchetype.actNum) {
                    case 1:
                        returnValue = CardLibrary.getCopy(CorpseExplosion.ID);
                        break;
                    case 2:
                        returnValue = CardLibrary.getCopy(AfterImage.ID);
                        break;
                    case 3:
                    default:
                        returnValue = CardLibrary.getCopy(StormOfSteel.ID);
                        break;
                }
            } else if (m instanceof CharBossDefect && ((CharBossDefect) m).chosenArchetype != null) {
                switch (((CharBossDefect) m).chosenArchetype.actNum) {
                    case 1:
                        returnValue = CardLibrary.getCopy(Buffer.ID);
                        break;
                    case 2:
                        returnValue = CardLibrary.getCopy(CoreSurge.ID);
                        break;
                    case 3:
                    default:
                        returnValue = CardLibrary.getCopy(Seek.ID);
                        break;
                }
            } else if (m instanceof CharBossWatcher && ((CharBossWatcher) m).chosenArchetype != null) {
                switch (((CharBossWatcher) m).chosenArchetype.actNum) {
                    case 1:
                        returnValue = CardLibrary.getCopy(ConjureBlade.ID);
                        break;
                    case 2:
                        returnValue = CardLibrary.getCopy(Ragnarok.ID);
                        break;
                    case 3:
                    default:
                        returnValue = CardLibrary.getCopy(Omniscience.ID);
                        break;
                }
            } else if (m instanceof CharBossHermit && ((CharBossHermit) m).chosenArchetype != null) {
                switch (((CharBossHermit) m).chosenArchetype.actNum) {
                    case 1:
                        returnValue = CardLibrary.getCopy(Adapt.ID);
                        break;
                    case 2:
                        returnValue = CardLibrary.getCopy(Magnum.ID);
                        break;
                    case 3:
                    default:
                        returnValue = CardLibrary.getCopy(FromBeyond.ID);
                        break;
                }
            } else {
                returnValue = new DefaultCollectibleCard();
            }
        }
        CardModifierManager.addModifier(returnValue, new CollectedCardMod());
        return returnValue;
    }

    public static void init() {
        collection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        combatCollection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }

    public static void atBattleStart() {
        combatCollection.clear();
        for (AbstractCard q : collection.group) {
            if (q instanceof AbstractCollectorCard){
                if (((AbstractCollectorCard)q).combatChargesRemaining > 0){
                    combatCollection.addToTop(q.makeSameInstanceOf());
                    ((AbstractCollectorCard) q).combatChargesRemaining --;
                }
            }
        }
        combatCollection.shuffle(AbstractDungeon.shuffleRng);
        ArrayList<AbstractCard> toTopdeck = new ArrayList<>();
        for (AbstractCard q : combatCollection.group) {
            if (CollectorBottleField.inCollectionBottle.get(q)) {
                toTopdeck.add(q);
            }
        }
        toTopdeck.forEach(q -> {
            combatCollection.removeCard(q);
            combatCollection.addToTop(q);
        });
    }

    public static void atBattleEnd() {
        combatCollection.clear();
    }

    public static void collect(AbstractMonster m) {
        if (!collectedAlready.contains(m) && !m.id.equals(NeowBoss.ID)) {
            /*
            if (AbstractDungeon.getCurrRoom().rewards.stream().noneMatch(q -> q instanceof EssenceReward)) {
                AbstractDungeon.getCurrRoom().rewards.add(new EssenceReward(getEssenceAmount(AbstractDungeon.getCurrRoom())));

            }
             */
            AbstractDungeon.getCurrRoom().rewards.add(new CollectibleCardReward(getCollectedCard(m)));
            collectedAlready.add(m);
        }

    }

    private static int getEssenceAmount(AbstractRoom room) {
        if (room instanceof MonsterRoomBoss) {
            return 3;
        } else if (room.eliteTrigger) {
            return 2;
        }
        return 1;
    }
}
