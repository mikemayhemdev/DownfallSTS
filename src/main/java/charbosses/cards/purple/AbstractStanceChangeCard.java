package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.stances.AbstractEnemyStance;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

abstract public class AbstractStanceChangeCard extends AbstractBossCard {
    public AbstractStanceChangeCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public AbstractStanceChangeCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, AbstractMonster.Intent intent) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target, intent);
    }

    public AbstractStanceChangeCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, AbstractMonster.Intent intent, boolean isCustomCard) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target, intent, isCustomCard);
    }

    public AbstractStanceChangeCard(AbstractCard baseCard) {
        super(baseCard);
    }

    abstract public AbstractEnemyStance changeStanceForIntentCalc(AbstractEnemyStance previousStance);
}
