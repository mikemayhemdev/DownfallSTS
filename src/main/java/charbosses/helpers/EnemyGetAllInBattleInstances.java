package charbosses.helpers;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.HashSet;
import java.util.UUID;

public class EnemyGetAllInBattleInstances {
    public static HashSet<AbstractCard> get(final UUID uuid) {
        final HashSet<AbstractCard> cards = new HashSet<AbstractCard>();
        if (AbstractCharBoss.boss.cardInUse.uuid.equals(uuid)) {
            cards.add(AbstractCharBoss.boss.cardInUse);
        }

        for (final AbstractCard c : AbstractCharBoss.boss.limbo.group) {
            if (!c.uuid.equals(uuid)) {
                continue;
            }
            cards.add(c);
        }
        for (final AbstractCard c : AbstractCharBoss.boss.hand.group) {
            if (!c.uuid.equals(uuid)) {
                continue;
            }
            cards.add(c);
        }
        return cards;
    }
}
