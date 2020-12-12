package champ.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import champ.ChampChar;
import champ.ChampMod;
import champ.actions.FatigueHpLossAction;
import champ.powers.CalledShotPower;
import champ.powers.ResolvePower;
import champ.stances.*;
import champ.util.OnOpenerSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.dailymods.ChampStances;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.List;

import static champ.ChampMod.*;
import static com.badlogic.gdx.graphics.Color.RED;


public abstract class AbstractChampCard extends CustomCard {

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected String DESCRIPTION;
    protected String UPGRADE_DESCRIPTION;
    protected String[] EXTENDED_DESCRIPTION;

    public int cool;
    public int baseCool;
    public boolean upgradedCool;
    public boolean isCoolModified;

    public int myHpLossCost;

    private boolean reInitDescription = true;

    public void resetAttributes() {
        super.resetAttributes();
        cool = baseCool;
        isCoolModified = false;
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

    public static boolean gcombo() {
        return (AbstractDungeon.player.stance.ID.equals(GladiatorStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public static boolean bcombo() {
        return (AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public static boolean dcombo() {
        return (AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || (AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)));
    }

    public int fatigue(int begone) {

        int y = AbstractDungeon.player.currentHealth;
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = Math.min(begone, AbstractDungeon.player.currentHealth - 1);
                att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(x), x));
                att(new FatigueHpLossAction(AbstractDungeon.player, AbstractDungeon.player, x));
            }
        });

        /*atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (y - AbstractDungeon.player.currentHealth > 0) {
                    att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(y - AbstractDungeon.player.currentHealth), y - AbstractDungeon.player.currentHealth));
                }
            }
        });
        */ //This unused method makes it so the player only gains Resolve equal to lost HP. Fixes some breakable things, but also unfun.

        return Math.min(begone, AbstractDungeon.player.currentHealth - 1);
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
        if (this.rawDescription.contains("champ:Fatigue") && !this.rawDescription.contains("champ:Resolve")) {
            tips.add(new TooltipInfo(ResolvePower.NAME, ChampChar.characterStrings.TEXT[39]));
        }
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
            this.glowColor = AbstractDungeon.player.stance instanceof AbstractChampStance ? RED : BLUE_BORDER_GLOW_COLOR;
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

    public void gladOpen() {
        gladiatorStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    public void defenseOpen() {
        defensiveStance();
        triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
    }

    protected void berserkerStance() {
        SlimeboundMod.logger.info("Switching to Berserker (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(BerserkerStance.STANCE_ID));
    }

    protected void gladiatorStance() {
        SlimeboundMod.logger.info("Switching to Gladiator (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(GladiatorStance.STANCE_ID));
    }

    protected void defensiveStance() {
        SlimeboundMod.logger.info("Switching to Defensive (Abstract)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            atb(new ChangeStanceAction(DefensiveStance.STANCE_ID));
    }

    protected void ultimateStance() {
        SlimeboundMod.logger.info("Switching to THE ULTIMATE STANCE!!! (Abstract)");
        atb(new ChangeStanceAction(UltimateStance.STANCE_ID));
    }

    public void exitStance() {
        SlimeboundMod.logger.info("Switching to Neutral (Abstract)");
        atb(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    public void techique() {
        if (AbstractDungeon.player.stance instanceof AbstractChampStance)
            ((AbstractChampStance) AbstractDungeon.player.stance).techique();
    }

    public void finisher() {

        ChampMod.finishersThisTurn++;
        ChampMod.finishersThisCombat++; //If there is a finishers this combat problem, maybe look here
        if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
            if (!AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID) && !(AbstractDungeon.player.stance instanceof UltimateStance)) {
                exitStance();
            }
            ((AbstractChampStance) AbstractDungeon.player.stance).fisher();
            if (AbstractDungeon.player.stance instanceof UltimateStance) {

            } else if (AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID)) {
                AbstractDungeon.player.getPower(CalledShotPower.POWER_ID).onSpecificTrigger();
            } else
                addToBot(new PressEndTurnButtonAction());
        }
    }

    @Override
    public void initializeDescription() {
        String prefixTech = "";
        String prefixFin = "";
        if (this.hasTag(TECHNIQUE)){
            prefixTech = ChampChar.characterStrings.TEXT[29];
            if (AbstractDungeon.player != null){
                if (AbstractDungeon.player.stance instanceof DefensiveStance){
                    prefixTech = ChampChar.characterStrings.TEXT[31];
                }
                else if (AbstractDungeon.player.stance instanceof GladiatorStance){
                    prefixTech = ChampChar.characterStrings.TEXT[30];
                }
                else if (AbstractDungeon.player.stance instanceof BerserkerStance){
                    prefixTech = ChampChar.characterStrings.TEXT[32];

                }
                else if (AbstractDungeon.player.stance instanceof UltimateStance){
                    prefixTech = ChampChar.characterStrings.TEXT[33];
                }
            }
            if (upgraded && this.UPGRADE_DESCRIPTION != null){
                this.rawDescription = prefixTech + UPGRADE_DESCRIPTION;
            } else {
                this.rawDescription = prefixTech + DESCRIPTION;
            }
        }
        if (this.hasTag(FINISHER)){
            prefixFin = ChampChar.characterStrings.TEXT[34];
            if (AbstractDungeon.player != null){
                if (AbstractDungeon.player.stance instanceof DefensiveStance){
                    prefixFin = ChampChar.characterStrings.TEXT[36];
                }
                else if (AbstractDungeon.player.stance instanceof GladiatorStance){
                    prefixFin = ChampChar.characterStrings.TEXT[35];
                }
                else if (AbstractDungeon.player.stance instanceof BerserkerStance){
                    prefixFin = ChampChar.characterStrings.TEXT[37];

                }
                else if (AbstractDungeon.player.stance instanceof UltimateStance){
                    prefixFin = ChampChar.characterStrings.TEXT[38];
                }
            }
            if (upgraded && this.UPGRADE_DESCRIPTION != null){
                this.rawDescription = prefixTech + prefixFin + UPGRADE_DESCRIPTION;
            } else {
                this.rawDescription = prefixTech + prefixFin + DESCRIPTION;
            }
        }
        super.initializeDescription();

    }

    @Override
    public void update() {
        if (this.reInitDescription){
            initializeDescription();
            this.reInitDescription = false;
        }
        super.update();
    }
}