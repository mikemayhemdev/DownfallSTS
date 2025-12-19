package gremlin.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import gremlin.GremlinMod;
import gremlin.patches.AbstractCardEnum;
import hermit.util.TextureLoader;

import static gremlin.GremlinMod.*;

public abstract class AbstractGremlinCard extends CustomCard {
    protected static String getID(String ID) {
        return "Gremlin:" + ID;
    }

    public boolean wizardry = false;
    public boolean sorcery = false;

    //Variables
    //Second Magic - Downfall Magic (DM)
    public int gremlinMagic;
    public int basegremlinMagic;
    public boolean isgremlinUpgraded;
    public boolean isgremlinModified;

    public boolean upgradedsecondMagic;
    public boolean issecondMagicModified;

    public boolean isBlamageModifed = false;
    public int baseBlamage = 0;
    public int blamage = 0;
    public boolean upgradedBlamage = false;

    public String betaArtPath;

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

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedBlamage) {
            blamage = baseBlamage;
            isBlamageModifed = true;
        }
        if (isgremlinUpgraded) {
            gremlinMagic = basegremlinMagic;
            isgremlinModified = true;
        }
    }

    protected void upgradeBlammage(int amount) {
        baseBlamage += amount;
        blamage = baseBlamage;
        upgradedBlamage = true;
    }


    public void resetAttributes() {
        super.resetAttributes();
        gremlinMagic = basegremlinMagic;
        isgremlinModified = false;
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

    public ART_GREMLIN getGremlinEnumFromCard(AbstractCard card) {
        if (!GremlinMod.isGremArtModeEnabled() && !(card instanceof GremlinDance)) {
            return ART_GREMLIN.NONE;
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

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    newPath = "gremlinResources/images/cards/betacards/" + newPath;
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }
}