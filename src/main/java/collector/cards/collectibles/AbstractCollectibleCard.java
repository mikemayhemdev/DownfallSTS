package collector.cards.collectibles;

import collector.cards.AbstractCollectorCard;
import collector.patches.CollectibleCardColorEnumPatch;

public abstract class AbstractCollectibleCard extends AbstractCollectorCard {
    public AbstractCollectibleCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target, CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE);
    }

}
