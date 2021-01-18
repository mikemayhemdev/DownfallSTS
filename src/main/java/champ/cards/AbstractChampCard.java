package champ.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import champ.ChampChar;
import champ.ChampMod;
import champ.ChampTextHelper;
import champ.powers.CalledShotPower;
import champ.relics.SignatureFinisher;
import champ.stances.AbstractChampStance;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import champ.util.OnFinisherSubscriber;
import champ.util.OnOpenerSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;

import java.util.ArrayList;
import java.util.List;

import static champ.ChampMod.*;


public abstract class AbstractChampCard extends CustomCard {

    protected final CardStrings cardStrings;
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


    public AbstractChampCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, ChampChar.Enums.CHAMP_GRAY, rarity, target);
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

    public static String makeID(String blah) {
        return getModID() + ":" + blah;
    }

    public static boolean bcombo() {
        return (enteredBerserkerThisTurn || AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public static boolean dcombo() {
        return (enteredDefensiveThisTurn || AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
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


    @Override
    public void triggerOnGlowCheck() {
        if (this.hasTag(FINISHER)) {
            this.glowColor = AbstractDungeon.player.stance instanceof AbstractChampStance ? Color.RED.cpy() : BLUE_BORDER_GLOW_COLOR;
        }
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
            if (!m.isDying && !m.isDead) q.add(m);
        }
        return q;
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
        berserkerStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public void defenseOpen() {
        defensiveStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    protected void berserkerStance() {
        //SlimeboundMod.logger.info("Switching to Berserker (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(BerserkerStance.STANCE_ID));
    }

    protected void defensiveStance() {
        //SlimeboundMod.logger.info("Switching to Defensive (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(DefensiveStance.STANCE_ID));
    }

    protected void ultimateStance() {
        //SlimeboundMod.logger.info("Switching to THE ULTIMATE STANCE!!! (Abstract)");
        atb(new ChangeStanceAction(UltimateStance.STANCE_ID));
    }

    public void exitStance() {
        //SlimeboundMod.logger.info("Switching to Neutral (Abstract)");
        atb(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    public void techique() {
        if (AbstractDungeon.player.stance instanceof AbstractChampStance)
            ((AbstractChampStance) AbstractDungeon.player.stance).techique();
    }

    public void finisher() {

        ChampMod.finishersThisTurn++;
        ChampMod.finishersThisCombat++; //If there is a finishers this combat problem, maybe look here

        if (!AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
            boolean leaveStance = true;
            if (AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID) || (AbstractDungeon.player.stance instanceof UltimateStance)) {
                leaveStance = false;
            }
            if (AbstractDungeon.player.hasRelic(SignatureFinisher.ID)) {
                SignatureFinisher s = (SignatureFinisher) AbstractDungeon.player.getRelic(SignatureFinisher.ID);
                if (s.card.uuid == this.uuid) {
                    leaveStance = false;
                }
            }
            if (leaveStance) {
                exitStance();
            }
            if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
                ((AbstractChampStance) AbstractDungeon.player.stance).fisher();
            }
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnFinisherSubscriber) {
                    ((OnFinisherSubscriber) p).onFinisher();
                }
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        ChampTextHelper.colorCombos(this, false);
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        ChampTextHelper.colorCombos(this, true);
    }

    @Override
    public void initializeDescription() {
        ChampTextHelper.calculateTagText(this);
        super.initializeDescription();
    }

    @Override
    public void update() {
        if (this.reInitDescription) {
            initializeDescription();
            this.reInitDescription = false;
        }
        super.update();
    }
}