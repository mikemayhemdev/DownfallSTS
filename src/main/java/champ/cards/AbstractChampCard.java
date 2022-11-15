package champ.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import champ.ChampChar;
import champ.relics.SignatureFinisher;
import champ.stances.UltimateStance;
import champ.stances.*;
import champ.util.OnOpenerSubscriber;
import champ.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;

import java.util.ArrayList;
import java.util.List;

import static champ.ChampMod.*;


public abstract class AbstractChampCard extends CustomCard {
    protected final CardStrings cardStrings;
    public String betaArtPath;
    protected final String NAME;
    public int cool;
    public int baseCool;
    public boolean upgradedCool;
    public boolean isCoolModified;
    public int myHpLossCost;
    public String DESCRIPTION;
    public String UPGRADE_DESCRIPTION;
    public String[] EXTENDED_DESCRIPTION;
    public boolean reInitDescription = true;

    public boolean techniqueLast = true;


    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, downfallMod.Enums.CHAMP_GRAY, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
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

    public static String getCorrectPlaceholderImage(CardType type, String id) {
        String img = makeCardPath(id.replaceAll((getModID() + ":"), "") + ".png");
        if ((!Gdx.files.internal(img).exists()))
            switch (type) {
                case ATTACK:
                    return makeCardPath("Attack.png");
                case SKILL:
                    return makeCardPath("Skill.png");
                case POWER:
                    return makeCardPath("Power.png");
            }
        return img;
    }

    public static String makeID(String name) {
        return getModID() + ":" + name;
    }

    public static boolean bcombo() {
        return (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID));
    }

    public static boolean dcombo() {
        return (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID));
    }

    public static boolean nostance() {
        return (AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public static boolean inBerserker() {
        return AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID);
    }

    public static boolean inGladiator() {
        return AbstractDungeon.player.stance.ID.equals(GladiatorStance.STANCE_ID);
    }

    public static boolean inDefensive() {
        return AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID);
    }

    public static void combo() {
        if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
            ((AbstractChampStance) AbstractDungeon.player.stance).combo();
        }
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedCool) {
            cool = baseCool;
            isCoolModified = true;
        }
    }

    void upgradeCool(int amount) {
        baseCool += amount;
        cool = baseCool;
        upgradedCool = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (this.rawDescription.contains("[crown_icon]") && !this.rawDescription.contains("champ:Technique")) {
            tips.add(new TooltipInfo(ChampChar.characterStrings.TEXT[40], ChampChar.characterStrings.TEXT[41]));
        }
        if (this.rawDescription.contains("[fist_icon]") && !this.rawDescription.contains("champ:Finisher")) {
            tips.add(new TooltipInfo(ChampChar.characterStrings.TEXT[42], ChampChar.characterStrings.TEXT[43]));
        }
        return tips;
    }

    public abstract void upp();

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

    public void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, makeInfo(), fx));
    }

    public void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void makeInHand(AbstractCard c, int i) {
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
        ArrayList<AbstractMonster> q = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) q.add(m);
        }
        return q;
    }

    public void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public WeakPower autoWeak(AbstractMonster m, int i) {
        return new WeakPower(m, i, false);
    }

    public VulnerablePower autoVuln(AbstractMonster m, int i) {
        return new VulnerablePower(m, i, false);
    }

    public void triggerOpenerRelics(boolean fromNeutral) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnOpenerSubscriber) ((OnOpenerSubscriber) r).onOpener(fromNeutral);
        }
    }

    @Override
    public void switchedStance() {
        initializeDescription();
        super.switchedStance();
    }

    public void berserkOpen() {
        //  berserkerStance();
        // triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public void defenseOpen() {
        //   defensiveStance();
        //  triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    protected void berserkerStance() {
        //SlimeboundMod.logger.info("Switching to Berserker (Abstract)");
        atb(new ChangeStanceAction(BerserkerStance.STANCE_ID));
    }

    protected void defensiveStance() {
        //SlimeboundMod.logger.info("Switching to Defensive (Abstract)");
        atb(new ChangeStanceAction(DefensiveStance.STANCE_ID));
    }

    protected void ultimateStance() {
        //SlimeboundMod.logger.info("Switching to THE ULTIMATE STANCE!!! (Abstract)");
        atb(new ChangeStanceAction(UltimateStance.STANCE_ID));
    }

    protected void gladiatorStance() {
        //SlimeboundMod.logger.info("Switching to Gladiator (Abstract)");
        atb(new ChangeStanceAction(GladiatorStance.STANCE_ID));
    }

    public void exitStance() {
        //SlimeboundMod.logger.info("Switching to Neutral (Abstract)");
        atb(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    @Override
    public void update() {
        if (this.reInitDescription) {
            initializeDescription();
            this.reInitDescription = false;
        }
        super.update();
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean bottled = false;
        if (hasTag(FINISHER)) {
            if (p.hasRelic(SignatureFinisher.ID)) {
                if ((((SignatureFinisher) p.getRelic(SignatureFinisher.ID)).card.uuid == this.uuid)) {
                    bottled = true;
                }
            }
            if (!bottled) {
                if ((AbstractDungeon.player.stance instanceof NeutralStance)) {
                    this.cantUseMessage = ChampChar.characterStrings.TEXT[61];
                    return false;
                }
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
                    newPath = "champResources/images/betacards/" + newPath;
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