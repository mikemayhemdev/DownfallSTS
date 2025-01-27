package collector.cards.collectibles;

import collector.cards.AbstractCollectorCard;
import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;

public abstract class AbstractCollectibleCard extends AbstractCollectorCard {
    public AbstractCollectibleCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(cardID, cost, type, rarity, target, CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE);
    }


    public AbstractCollectibleCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }
}
