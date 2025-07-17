package awakenedOne.cards;

import awakenedOne.AwakenedOneChar;
import awakenedOne.AwakenedOneMod;
import awakenedOne.AwakenedTextHelper;
import awakenedOne.powers.RisingChantPower;
import awakenedOne.relics.CursedBlessing;
import awakenedOne.relics.WhiteRibbon;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hermit.util.TextureLoader;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.*;

public abstract class AbstractAwakenedCard extends CustomCard {

    protected final CardStrings cardStrings;
    public String betaArtPath;

    public int secondMagic;
    public int baseSecondMagic = -1;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic = -1;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int secondDamage;
    public int baseSecondDamage = -1;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public boolean trig_chant = false;

    public AbstractAwakenedCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, AwakenedOneChar.Enums.AWAKENED_BLUE);
    }

    public AbstractAwakenedCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(AwakenedOneMod.getModID() + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();
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
                    System.out.println("Finding texture: " + newPath);
                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);
                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    public static String getCardTextureString(final String cardName, final CardType cardType) {
        String textureString = "awakenedResources/images/cards/" + cardName + ".png";
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            AwakenedTextHelper.colorCombos(this, false);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else {
            super.applyPowers();
            AwakenedTextHelper.colorCombos(this, false);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
            if (cardStrings.UPGRADE_DESCRIPTION != null) {
                rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                initializeDescription();
            }
        }
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        if (this.hasTag(ACTIVECHANT)) {
            this.keywords.add("awakened:chant");
        }
    }

    public abstract void upp();

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    //Chant Stuff
    public void chant() {
    }

    public void onVictory() {
        if (this.hasTag(ACTIVECHANT)) this.tags.remove(ACTIVECHANT);
        this.initializeDescription();
    }

    public boolean isChantActiveGlow(AbstractCard source) {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type == CardType.POWER || this.hasTag(ACTIVECHANT) || this.trig_chant) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard original = super.makeStatEquivalentCopy();
        ((AbstractAwakenedCard)original).trig_chant = this.trig_chant;
        if (this.trig_chant) {
            original.tags.add(ACTIVECHANT);
        }
        return original;
    }

    public boolean isTrig_chant() {
        if (isChantActive() || this.hasTag(ACTIVECHANT)) {
            this.trig_chant = true;
            AwakenedTextHelper.colorCombos(this, false);
        }
        return (trig_chant);
    }

    //Whenever a Chant effect activates, do this!!!
    public void checkOnChant() {
        if (!this.hasTag(ACTIVECHANT)) {
            this.tags.add(ACTIVECHANT);
            if (AbstractDungeon.player.hasRelic(CursedBlessing.ID)) {
                AbstractDungeon.player.getRelic(CursedBlessing.ID).onTrigger();
            }

            AwakenedTextHelper.colorCombos(this, false);
            this.initializeDescription();
        }

        if (AbstractDungeon.player.hasRelic(WhiteRibbon.ID)) {
           AbstractDungeon.player.getRelic(WhiteRibbon.ID).onTrigger();
        }
        if (AbstractDungeon.player.hasPower(RisingChantPower.POWER_ID)) {
            AbstractDungeon.player.getPower(RisingChantPower.POWER_ID).onSpecificTrigger();
        }
        AwakenedTextHelper.colorCombos(this, false);
    }

    public void update() {
        super.update();
    }
}
