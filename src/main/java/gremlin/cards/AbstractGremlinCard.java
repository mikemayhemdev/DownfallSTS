package gremlin.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gremlin.GremlinMod;
import gremlin.patches.AbstractCardEnum;

public abstract class AbstractGremlinCard extends CustomCard {
    protected static String getID(String ID) {
        return "Gremlin:" + ID;
    }

    public boolean wizardry = false;
    public boolean sorcery = false;

    protected AbstractGremlinCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                                  CardRarity rarity, CardTarget target) {
        super(id, name, GremlinMod.getResourcePath(img), cost, rawDescription, type,
                AbstractCardEnum.GREMLIN, rarity, target);
    }

    public void onGremlinSwap() {
    }

    public void onGremlinSwapInDeck() {
    }

    public void setBackgrounds() {
        //Call this immediately after any tag changes.
        switch (this.type) {
            case SKILL:
                this.setBackgroundTexture("gremlinResources/images/512/bg_skill_gremlin_" + getGremlinEnumFromCard(this).name().toLowerCase() + ".png", "gremlinResources/images/1024/bg_skill_gremlin_" + getGremlinEnumFromCard(this).name().toLowerCase() + ".png");
                break;
            case POWER:
                this.setBackgroundTexture("gremlinResources/images/512/bg_power_gremlin_" + getGremlinEnumFromCard(this).name().toLowerCase() + ".png", "gremlinResources/images/1024/bg_power_gremlin_" + getGremlinEnumFromCard(this).name().toLowerCase() + ".png");
                break;
            case ATTACK:
                this.setBackgroundTexture("gremlinResources/images/512/bg_attack_gremlin_" + getGremlinEnumFromCard(this).name().toLowerCase() + ".png", "gremlinResources/images/1024/bg_attack_gremlin_" + getGremlinEnumFromCard(this).name().toLowerCase() + ".png");
                break;
        }
    }

    public enum ART_GREMLIN {
        FAT,
        SNEAKY,
        TSUNDERE,
        WIZ,
        THIEF,
        NOB
    }

    public static ART_GREMLIN getGremlinEnumFromCard(AbstractCard card) {
        // Here is where you or I can implement the function that takes a card,
        // and based off of its tags, returns the corresponding ART_GREMLIN enum.
        return ART_GREMLIN.FAT;
    }
}


