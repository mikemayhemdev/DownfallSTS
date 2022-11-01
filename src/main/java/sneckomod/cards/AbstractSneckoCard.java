package sneckomod.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import hermit.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import sneckomod.powers.CheatPower;
import sneckomod.relics.D8;
import sneckomod.relics.LoadedDie;

import java.util.ArrayList;
import java.util.List;

import static sneckomod.SneckoMod.getModID;
import static sneckomod.SneckoMod.makeCardPath;


public abstract class AbstractSneckoCard extends CustomCard {

    protected String[] unknownUpgrade = CardCrawlGame.languagePack.getUIString(makeID("Unknown")).TEXT;
    protected String[] unknownNames = CardCrawlGame.languagePack.getUIString(makeID("UnknownNames")).TEXT;

    protected final CardStrings cardStrings;
    public String betaArtPath;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;
    public int silly;
    public int baseSilly;
    public boolean upgradedSilly;
    public boolean isSillyModified;


    public AbstractSneckoCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(id),
                cost, "ERROR", type, downfallMod.Enums.SNECKO_CYAN, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractSneckoCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
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

    public AbstractSneckoCard(final String id, final String img,  final int cost, final CardType type, final CardRarity rarity, final CardTarget target, boolean IsClass) {
        super(id, "ERROR", getCorrectPlaceholderImage(img),
                cost, "ERROR", type, downfallMod.Enums.SNECKO_CYAN, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings("sneckomod:Unknown0Cost");
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractSneckoCard(final String id, final String img, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(img),
                cost, "ERROR", type, downfallMod.Enums.SNECKO_CYAN, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractSneckoCard(final String id, final String img, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, "ERROR", getCorrectPlaceholderImage(img),
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

    public static String getCorrectPlaceholderImage(String id) {
        return makeCardPath(id.replaceAll((getModID() + ":"), "")) + ".png";
    }

    public static int getRandomNum(int min, int max) {
        int a, b, sum;
        if (min > max) {
            a = max;
            b = min;
        } else {
            a = min;
            b = max;
        }
        if (a != b) {
            sum = AbstractDungeon.cardRandomRng.random(a, b);
        } else {
            sum = b;
        }
        return sum;
    }


    public static int getRandomNum(int min, int max, AbstractSneckoCard source) {

        int bruh = min;
        if (AbstractDungeon.player.hasPower(CheatPower.POWER_ID)) {
            AbstractPower q = AbstractDungeon.player.getPower(CheatPower.POWER_ID);
            q.flash();
            return max;
        }
        if (AbstractDungeon.player.hasRelic(D8.ID)) {
            //SlimeboundMod.logger.info("min/max check passed D8 relic check");
            if (source != null) {
                //SlimeboundMod.logger.info("min/max check passed card source check");
                D8 d8relic = (D8) AbstractDungeon.player.getRelic(D8.ID);
                if (d8relic.card == source)
                    //SlimeboundMod.logger.info("min/max check passed card source = bottled card check");
                    return max;
            }
        }
        if (AbstractDungeon.player.hasRelic(LoadedDie.ID))
            bruh++;

        int a, b, sum;
        if (bruh > max) {
            a = max;
            b = bruh;
        } else {
            a = bruh;
            b = max;
        }
        if (a != b) {
            sum = AbstractDungeon.cardRandomRng.random(a, b);
        } else {
            sum = b;
        }
        return sum;
    }

    public static String makeID(String name) {
        return getModID() + ":" + name;
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
    public void resetAttributes() {
        super.resetAttributes();
        silly = baseSilly;
        isSillyModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSilly) {
            silly = baseSilly;
            isSillyModified = true;
        }
    }


    void upgradeSilly(int amount) {
        baseSilly += amount;
        silly = baseSilly;
        upgradedSilly = true;
    }

    public static String getCharList() {
        StringBuilder s = new StringBuilder();
        for (CardColor c : SneckoMod.validColors) {
            s.append(" NL ").append(SneckoMod.getClassFromColor(c));
        }
        return s.toString();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        for (String name : unknownNames) {
            if (this.rawDescription.contains(name)) {
                if (SneckoMod.validColors.size() > 3) {
                    tips.add(new TooltipInfo(unknownUpgrade[0], unknownUpgrade[5]));
                }
                else if (SneckoMod.validColors.isEmpty()) {
                    tips.add(new TooltipInfo(unknownUpgrade[0], unknownUpgrade[4]));
                } else {
                    tips.add(new TooltipInfo(unknownUpgrade[0], unknownUpgrade[2] + unknownUpgrade[3] + getCharList()));
                }
            }
        }
        return tips;
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
                    newPath = "sneckomodResources/images/betacards/" + newPath;
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