package expansioncontent.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.CardColorEnumPatch;
import hermit.util.TextureLoader;

import static expansioncontent.expansionContentMod.*;

public abstract class AbstractExpansionCard extends AbstractDownfallCard {
    public static final String CannotUseBossCardMessage = CardCrawlGame.languagePack.getUIString(makeID("CannotUseBossCardMessage")).TEXT[0];
    public String betaArtPath;

    public AbstractExpansionCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, getCorrectPlaceholderImage(id), cost, type, rarity, target, CardColorEnumPatch.CardColorPatch.BOSS);
        setFrame();
    }

    public AbstractExpansionCard(final String id, final String img, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, getCorrectPlaceholderImage(img), cost, type, rarity, target, CardColorEnumPatch.CardColorPatch.BOSS);
        setFrame();
    }

    public AbstractExpansionCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, getCorrectPlaceholderImage(id), cost, type, rarity, target, color);
        setFrame();
    }

    public void setFrame(){
        if (this.type == CardType.ATTACK){
            this.setBackgroundTexture("expansionContentResources/images/512/bg_attack_evil.png", "expansionContentResources/images/1024/bg_attack_evil.png");
        } else if (this.type == CardType.SKILL){
            this.setBackgroundTexture("expansionContentResources/images/512/bg_skill_evil.png", "expansionContentResources/images/1024/bg_skill_evil.png");
        } else {
            this.setBackgroundTexture("expansionContentResources/images/512/bg_power_evil.png", "expansionContentResources/images/1024/bg_power_evil.png");
        }
    }

    private static String getCorrectPlaceholderImage(String id) {
        return makeCardPath(id.replaceAll((expansionContentMod.getModID() + ":"), "")) + ".png";
    }

    public static String makeID(String name) {
        return getModID() + ":" + name;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (hasTag(STUDY)) {
            if (!downfallMod.playedBossCardThisTurn)
                return super.canUse(p, m);
            else {
                cantUseMessage = CannotUseBossCardMessage;
                return false;
            }
        }
        return super.canUse(p, m);
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
                    newPath = "expansioncontentResources/images/betacards/" + newPath;
                    System.out.println("Finding texture: " + newPath);

                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);

                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    @Override
    public void upp() {} //TODO Remove me later
}