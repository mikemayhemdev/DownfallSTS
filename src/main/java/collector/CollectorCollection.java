package collector;

import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import collector.cards.Collectibles.*;
import collector.util.CollectionReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.*;

import java.util.HashMap;

public class CollectorCollection {
    public static CardGroup collection;
    public static CardGroup combatCollection;

    public static HashMap<String, AbstractCard> cardsList;

    public static void init() {
        cardsList = new HashMap<>();
        cardsList.put(GremlinWizard.ID, new CrookedStaff());
        cardsList.put(GremlinWarrior.ID, new CrudeShield());
        cardsList.put(Cultist.ID, new CultistFeather());
        cardsList.put(GremlinFat.ID, new CurledHorns());
        cardsList.put(CharBossDefect.ID, new DefectCore());
        cardsList.put(GremlinThief.ID, new GremlinPoker());
        cardsList.put(CharBossIronclad.ID, new IroncladMask());
        cardsList.put(JawWorm.ID, new JarWormTooth());
        cardsList.put(Lagavulin.ID, new LagavullinShell());
        cardsList.put(LouseNormal.ID, new LouseSegment());
        cardsList.put(LouseDefensive.ID, new LouseSegment());
        cardsList.put(GremlinNob.ID, new NobsBoneClub());
        cardsList.put(Sentry.ID, new SentryCore());
        cardsList.put(CharBossSilent.ID, new SilentTrophy());
        cardsList.put(AcidSlime_L.ID, new SlimeSample());
        cardsList.put(AcidSlime_M.ID, new SlimeSample());
        cardsList.put(AcidSlime_S.ID, new SlimeSample());
        cardsList.put(SpikeSlime_L.ID, new VialofOoze());
        cardsList.put(SpikeSlime_M.ID, new VialofOoze());
        cardsList.put(SpikeSlime_S.ID, new VialofOoze());
        cardsList.put(GremlinTsundere.ID, new ViciousClaws());
        cardsList.put(CharBossWatcher.ID, new WatchersStaff());

        collection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        collection.addToBottom(new LuckyWick());
        collection.addToBottom(new LuckyWick());
        collection.addToBottom(new LuckyWick());
        collection.addToBottom(new LuckyWick());
    }

    public static void atBattleStart() {
        combatCollection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard q : collection.group) {
            combatCollection.addToTop(q.makeSameInstanceOf());
        }
    }

    public static void atBattleEnd() {
        combatCollection.clear();
    }

    public static void GetCollectible(AbstractMonster collectedMonster) {
        if (cardsList.containsKey(collectedMonster.id)) {
            AbstractCard NewCollectible = cardsList.get(collectedMonster.id).makeStatEquivalentCopy();
            CollectionReward.collectPool.add(NewCollectible);
        } else CollectionReward.collectPool.add(new LuckyWick());
    }
}
