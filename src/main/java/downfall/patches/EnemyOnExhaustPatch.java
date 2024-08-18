package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

// Copied from Packmaster distortion pack
@SpirePatch(
        clz = CardGroup.class,
        method = "moveToExhaustPile"
)
public class EnemyOnExhaustPatch {
    public interface EnemyOnExhaustPower {
        void enemyOnExhaust(AbstractCard c);
    }

    @SpirePrefixPatch
    public static void TriggerEnemyPowers(CardGroup __instance, AbstractCard c) {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDying && !m.isEscaping)
                    for (AbstractPower p : m.powers)
                        if (p instanceof EnemyOnExhaustPower)
                            ((EnemyOnExhaustPower) p).enemyOnExhaust(c);
            }
        }
    }
}
