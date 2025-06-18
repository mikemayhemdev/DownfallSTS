package expansioncontent.cards;

import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import expansioncontent.patches.CardColorEnumPatch;
import hermit.util.TextureLoader;
import theHexaghost.HexaMod;
import theHexaghost.cards.AbstractHexaCard;
import theHexaghost.vfx.AfterlifePlayEffect;

import static expansioncontent.expansionContentMod.*;

public abstract class AbstractExpansionCard extends AbstractDownfallCard {
    public static final String CannotUseBossCardMessage = CardCrawlGame.languagePack.getUIString(makeID("CannotUseBossCardMessage")).TEXT[0];
    public String betaArtPath;
    protected final String UPGRADE_DESCRIPTION;
    protected final CardStrings cardStrings;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;

    public AbstractExpansionCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(id),
                cost, "ERROR", type, CardColorEnumPatch.CardColorPatch.BOSS, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
        setFrame();
    }

    public AbstractExpansionCard(final String id, final String img, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(img),
                cost, "ERROR", type, CardColorEnumPatch.CardColorPatch.BOSS, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
        setFrame();
    }

    public AbstractExpansionCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, "ERROR", getCorrectPlaceholderImage(id),
                cost, "ERROR", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
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
    public void triggerOnExhaust() {
        int bonus = 0;

        att(new AbstractGameAction() {
            @Override
            public void update() {
                if (useAfterlifeVFX() && duration == startDuration) {
                    atb(new VFXAction(new AfterlifePlayEffect(AbstractExpansionCard.this)));
                }
                tickDuration();
                if (isDone) {

                    atb(new WaitAction(0.2F)); // from ShowCardAction

                    applyPowers();
                    afterlife();

                    atb(new WaitAction(0.15F)); // from UseCardAction

                    if (type == AbstractCard.CardType.POWER) { // special case for powers in UseCardAction
                        if (com.megacrit.cardcrawl.core.Settings.FAST_MODE) {
                            atb(new WaitAction(0.1F));
                        } else {
                            atb(new WaitAction(0.7F));
                        }
                    }
                }
            }
        });

    }

    protected boolean useAfterlifeVFX() {
        return AbstractExpansionCard.this.tags.contains(HexaMod.AFTERLIFE);
    }

    public void afterlife() {}

    public boolean isChantActive(AbstractCard source) {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type == CardType.POWER) {
            return true;
        } else {
            return false;
        }
    }

    //collector pyre code
    protected void isPyre() {
        CardModifierManager.addModifier(this, new PyreMod());
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
}