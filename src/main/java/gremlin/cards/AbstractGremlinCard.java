package gremlin.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import gremlin.GremlinMod;
import gremlin.patches.AbstractCardEnum;

import static gremlin.GremlinMod.*;

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
        setBackgrounds();
    }

    public void onGremlinSwap() {
    }

    public void onGremlinSwapInDeck() {
    }

    public void setBackgrounds() {
        //Call this immediately after any tag changes.
        if (getGremlinEnumFromCard(this).equals(ART_GREMLIN.NONE)) {
            switch (this.type) {
                case SKILL:
                    this.setBackgroundTexture("gremlinResources/images/512/bg_skill_gremlin.png", "gremlinResources/images/1024/bg_skill_gremlin.png");
                    break;
                case POWER:
                    this.setBackgroundTexture("gremlinResources/images/512/bg_power_gremlin.png", "gremlinResources/images/1024/bg_power_gremlin.png");
                    break;
                case ATTACK:
                    this.setBackgroundTexture("gremlinResources/images/512/bg_attack_gremlin.png", "gremlinResources/images/1024/bg_attack_gremlin.png");
                    break;
            }
        } else {
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
    }

    public enum ART_GREMLIN {
        FAT,
        SNEAKY,
        TSUNDERE,
        WIZ,
        THIEF,
        NOB,
        NONE
    }

    public static ART_GREMLIN getGremlinEnumFromCard(AbstractCard card) {
        // Make config logic somehow?
        if (card.type.equals(CardType.ATTACK)) {
            return ART_GREMLIN.THIEF;
        }
        if (card.type.equals(CardType.SKILL)) {
            return ART_GREMLIN.TSUNDERE;
        }
        if (card.type.equals(CardType.POWER)) {
            return ART_GREMLIN.WIZ;
        }

        // Here is where you or I can implement the function that takes a card,
        // and based off of its tags, returns the corresponding ART_GREMLIN enum.
        if (card.tags.contains(FAT_GREMLIN)) {
            return ART_GREMLIN.FAT;
        }
        if (card.tags.contains(MAD_GREMLIN)) {
            return ART_GREMLIN.THIEF;
        }
        if (card.tags.contains(SHIELD_GREMLIN)) {
            return ART_GREMLIN.TSUNDERE;
        }
        if (card.tags.contains(SNEAKY_GREMLIN)) {
            return ART_GREMLIN.SNEAKY;
        }
        if (card.tags.contains(WIZARD_GREMLIN)) {
            return ART_GREMLIN.WIZ;
        }
        if (card.tags.contains(NOB_GREMLIN)) {
            return ART_GREMLIN.NOB;
        }
        return ART_GREMLIN.NONE;
    }
}


