package gremlin.cards;

import basemod.abstracts.CustomCard;
import gremlin.GremlinMod;
import gremlin.patches.AbstractCardEnum;

public abstract class AbstractGremlinCard extends CustomCard {
    protected static String getID(String ID){
        return "Gremlin:"+ID;
    }

    public boolean wizardry = false;
    public boolean sorcery = false;

    protected AbstractGremlinCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                          CardRarity rarity, CardTarget target) {
        super(id, name, GremlinMod.getResourcePath(img), cost, rawDescription, type,
                AbstractCardEnum.GREMLIN, rarity, target);
    }

    public void onGremlinSwap() {}
    public void onGremlinSwapInDeck() {}
}


