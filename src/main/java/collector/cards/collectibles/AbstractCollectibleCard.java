package collector.cards.collectibles;

import collector.cards.AbstractCollectorCard;
import downfall.downfallMod;

public abstract class AbstractCollectibleCard extends AbstractCollectorCard {
    public AbstractCollectibleCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

}
