package theHexaghost.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hermit.util.TextureLoader;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.powers.BurnPower;
import theHexaghost.powers.CrispyPower;
import theHexaghost.powers.ParanormalFormPower;
import theHexaghost.relics.CandleOfCauterizing;
import theHexaghost.vfx.AfterlifePlayEffect;

import java.util.ArrayList;

import static theHexaghost.HexaMod.getModID;
import static theHexaghost.HexaMod.makeCardPath;

public abstract class AbstractHexaCard extends CustomCard {

    protected final CardStrings cardStrings;
    public String betaArtPath;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected final String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;

    public int burn;
    public int baseBurn;
    public boolean upgradedBurn;
    public boolean isBurnModified;

    public AbstractHexaCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(id),
                cost, "ERROR", type, TheHexaghost.Enums.GHOST_GREEN, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractHexaCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
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
    }

    private static String getCorrectPlaceholderImage(String id) {
        return makeCardPath(id.replaceAll((HexaMod.getModID() + ":"), "")) + ".png";
    }

    public static String makeID(String blah) {
        return getModID() + ":" + blah;
    }

    protected void atb(AbstractGameAction action) {
        addToBot(action);
    }

    protected void att(AbstractGameAction action) {
        addToTop(action);
    }

    protected DamageInfo makeInfo() {
        return makeInfo(damageTypeForTurn);
    }

    private DamageInfo makeInfo(DamageInfo.DamageType type) {
        return new DamageInfo(AbstractDungeon.player, damage, type);
    }

    public void dmg(AbstractMonster m, DamageInfo info, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, makeInfo(), fx));
    }

    public void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    private void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public ArrayList<AbstractMonster> monsterList() {
        return AbstractDungeon.getMonsters().monsters;
    }

    public void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    WeakPower autoWeak(AbstractMonster m, int i) {
        return new WeakPower(m, i, false);
    }

    VulnerablePower autoVuln(AbstractMonster m, int i) {
        return new VulnerablePower(m, i, false);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int base = this.baseBurn;
        if (AbstractDungeon.player.hasPower(CrispyPower.POWER_ID)) {
            base += AbstractDungeon.player.getPower(CrispyPower.POWER_ID).amount;
        }
        if(AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)){
            base += CandleOfCauterizing.SOULBURN_BONUS_AMOUNT;
        }
        this.burn = base;
        this.isBurnModified = (this.burn != this.baseBurn);
    }

    public void burn(AbstractMonster m, int amount) {
        applyToEnemy(m, new BurnPower(m, amount));
        if(AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)){
            AbstractRelic r = AbstractDungeon.player.getRelic(CandleOfCauterizing.ID);
            r.flash();
        }
    }

    public void resetAttributes() {
        super.resetAttributes();
        burn = baseBurn;
        isBurnModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedBurn) {
            burn = baseBurn;
            isBurnModified = true;
        }
    }

    void upgradeBurn(int amount) {
        baseBurn += amount;
        burn = baseBurn;
        upgradedBurn = true;
    }

    protected void burnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.getCurrRoom().monsters != null)
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.hasPower(BurnPower.POWER_ID)) {
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                    break;
                }
            }
    }

    @Override
    public void triggerOnExhaust() {
        int bonus = 0;
//        if(AbstractDungeon.player.hasPower(ParanormalFormPower.POWER_ID ) && this.hasTag(HexaMod.AFTERLIFE) ){
//            bonus = AbstractDungeon.player.getPower(ParanormalFormPower.POWER_ID).amount;
//        }

        for(int i = 0; i < bonus + 1; i++) {
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    if (useAfterlifeVFX() && duration == startDuration) {
                        atb(new VFXAction(new AfterlifePlayEffect(AbstractHexaCard.this)));
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
    }

    protected boolean useAfterlifeVFX() {
        return AbstractHexaCard.this.tags.contains(HexaMod.AFTERLIFE);
    }

    public void afterlife() {}

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    newPath = "hexamodResources/images/betacards/" + newPath;
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
