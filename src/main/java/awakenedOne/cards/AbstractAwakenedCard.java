package awakenedOne.cards;

import awakenedOne.AwakenedOneChar;
import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.RisingChantPower;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.CardArtRoller;
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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.actions.PseudoDamageRandomEnemyAction;

import static awakenedOne.util.Wiz.*;

public abstract class AbstractAwakenedCard extends CustomCard {

    protected final CardStrings cardStrings;
    public String betaArtPath;
    private boolean needsArtRefresh = false;

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

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.getAllCards() != null && !CardLibrary.getAllCards().isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final CardType cardType) {
        String textureString = "awakenedResources/images/cards/" + cardName + ".png";
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = "awakenedResources/images/ui/missing.png";
        }
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

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
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

    public abstract void upp();

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void chant() {
    }

    //Whenever a Chant effect activates, do this!!!
    public static void checkOnChant() {
        if (AbstractDungeon.player.hasPower(RisingChantPower.POWER_ID)) {
            applyToSelf(new StrengthPower(AbstractDungeon.player, AbstractDungeon.player.getPower(RisingChantPower.POWER_ID).amount));
        }

        if (AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
           AbstractDungeon.player.getRelic(KTRibbon.ID).onTrigger();
        }

    }


    protected AbstractCard cardForGen(String ID) {
        AbstractCard q = CardLibrary.getCard(ID).makeCopy();
        if (upgraded) q.upgrade();
        return q;
    }

    public void update() {
        super.update();
        if (needsArtRefresh) {
            CardArtRoller.computeCard(this);
        }
    }
}
