package charbosses.helpers;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;

import charbosses.bosses.AbstractCharBoss;

import java.util.*;

public class EnemyGetAllInBattleInstances
{
    public static HashSet<AbstractCard> get(final UUID uuid) {
        final HashSet<AbstractCard> cards = new HashSet<AbstractCard>();
        if (AbstractCharBoss.boss.cardInUse.uuid.equals(uuid)) {
            cards.add(AbstractCharBoss.boss.cardInUse);
        }
        for (final AbstractCard c : AbstractCharBoss.boss.drawPile.group) {
            if (!c.uuid.equals(uuid)) {
                continue;
            }
            cards.add(c);
        }
        for (final AbstractCard c : AbstractCharBoss.boss.discardPile.group) {
            if (!c.uuid.equals(uuid)) {
                continue;
            }
            cards.add(c);
        }
        for (final AbstractCard c : AbstractCharBoss.boss.exhaustPile.group) {
            if (!c.uuid.equals(uuid)) {
                continue;
            }
            cards.add(c);
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
