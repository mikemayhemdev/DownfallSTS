package collector.cards.Collectibles;

import automaton.FunctionHelper;
import automaton.cardmods.EncodeMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import collector.patches.CollectibleCardColorEnumPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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

import java.util.ArrayList;

import static automaton.AutomatonMod.makeCardPath;
import static automaton.FunctionHelper.WITH_DELIMITER;

public abstract class AbstractCollectibleCard extends CustomCard {

    private static float functionPreviewCardScale = .9f;
    private static float functionPreviewCardY = Settings.HEIGHT * 0.45F;
    private static float functionPreviewCardX = Settings.WIDTH * 0.1F;
    protected final CardStrings cardStrings;
    protected final String NAME;
    public int auto;
    public int baseAuto;
    public boolean upgradedAuto;
    public boolean isAutoModified;
    public boolean isHoveredInSequence = false;
    public int position = -1;
    public boolean doSpecialCompileStuff = true;
    protected String DESCRIPTION;
    protected String UPGRADE_DESCRIPTION;
    protected String[] EXTENDED_DESCRIPTION;
    private AbstractCard functionPreviewCard;

    public AbstractCollectibleCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractCollectibleCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
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
        String img = makeCardPath(id.replaceAll(("collector:"), "") + ".png");
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
        return "collector:" + blah;
    }

    public void resetAttributes() {
        super.resetAttributes();
        auto = baseAuto;
        isAutoModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedAuto) {
            auto = baseAuto;
            isAutoModified = true;
        }

    }

    void upgradeAuto(int amount) {
        baseAuto += amount;
        auto = baseAuto;
        upgradedAuto = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public void fineTune() {
        this.baseDamage += 1;
        this.damage += 1;
        this.baseBlock += 1;
        this.block += 1;
        this.baseMagicNumber += 1;
        this.magicNumber += 1;
        this.baseAuto += 1;
        this.auto += 1;
        this.superFlash();
        FunctionHelper.genPreview();
    }

    public void onInput() {
        // Called right after a card enters The Function, before outputs would occur.
        // Pro tip: Don't delete things just because they're unused..
    }

    public void onCompilePreCardEffectEmbed(boolean forGameplay) {
        // Called before the effects of cards are added to the Function. Use this if a card modifies its statistics as a Compile effect. Don't put these on action queue.
    }

    public void onCompile(AbstractCard function, boolean forGameplay) {
        // Called when the function is about to be created. Watch out, onCompile() is called in order of insertion.
    }

    public void onCompileLast(AbstractCard function, boolean forGameplay) {
        // Called after all cards have done onCompile, before relics and powers have.
    }

    public void doNothingSpecificInParticular() {
        initializeTitle();
    }

    public String getNoun() {
        return EXTENDED_DESCRIPTION[1];
    }

    public String getAdjective() {
        return EXTENDED_DESCRIPTION[0];
    }

    public String getBonusChar() {
        return String.valueOf(NAME.charAt(0));
    }

    public String getSpecialCompileText() {
        String[] splitText = this.rawDescription.split("bronze:Compile");
        String compileText = splitText[1];
        return (compileText.replaceAll("bronze:", "#y").replaceAll("!D!", String.valueOf(this.damage)).replaceAll("!B!", String.valueOf(this.block)).replaceAll("!M!", String.valueOf(this.magicNumber)).replaceAll("!bauto!", (String.valueOf(this.auto))).replace("*", "#y"));
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
        atb(new MakeTempCardInDrawPileAction(c, i, true, true)); // This is suspect
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

    public void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    WeakPower autoWeak(AbstractMonster m, int i) {
        return new WeakPower(m, i, false);
    }

    VulnerablePower autoVuln(AbstractMonster m, int i) {
        return new VulnerablePower(m, i, false);
    }

    void thisEncodes() {
        CardModifierManager.addModifier(this, new EncodeMod());
    }

    public int getSequencePosition() {
        if (FunctionHelper.held != null) {
            if (FunctionHelper.held.contains(this)) {
                return FunctionHelper.held.group.indexOf(this);
            }
        }
        return -1;
    }

    @Override
    public void hover() {
        if ((getSequencePosition() >= 0 || FunctionHelper.secretStorage == this) && !isHoveredInSequence) {
            isHoveredInSequence = true;
            //SlimeboundMod.logger.info("hover() hit");
            ReflectionHacks.setPrivate(this, AbstractCard.class, "hovered", true);

        } else {
            super.hover();
        }

    }

    @Override
    public void unhover() {
        if ((getSequencePosition() >= 0 || FunctionHelper.secretStorage == this) && isHoveredInSequence) {
            isHoveredInSequence = false;
            //SlimeboundMod.logger.info("unhover() hit");
            ReflectionHacks.setPrivate(this, AbstractCard.class, "hovered", false);

        } else {
            super.unhover();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        // SlimeboundMod.logger.info("rendering cardTip");

        if (isHoveredInSequence) {
            // SlimeboundMod.logger.info("isHoveredInSequence");
            if (isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {

                // SlimeboundMod.logger.info("bounced");
                return;
            }


            // SlimeboundMod.logger.info("Is in Sequence");
            if (functionPreviewCard == null) {
                functionPreviewCard = makeStatEquivalentCopy();
            }
            //SlimeboundMod.logger.info("rendering previewcard");
            functionPreviewCard.drawScale = functionPreviewCardScale;
            functionPreviewCard.current_x = functionPreviewCardX;
            functionPreviewCard.current_y = functionPreviewCardY;
            // functionPreviewCard.update();
            functionPreviewCard.render(sb);


        }
        if (!hb.hovered && functionPreviewCard != null) {
            functionPreviewCard = null;
        }
    }

    boolean lastCard() {
        return position == FunctionHelper.max - 1;
    }

    boolean firstCard() {
        return position == 0;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard r = super.makeStatEquivalentCopy();
        if (!this.doSpecialCompileStuff && r instanceof AbstractCollectibleCard) {
            ((AbstractCollectibleCard) r).doSpecialCompileStuff = false;
            if (r.rawDescription.contains(" NL bronze:Compile")) {
                String[] splitText = r.rawDescription.split(String.format(WITH_DELIMITER, " NL bronze:Compile"));
                String compileText = splitText[1] + splitText[2];
                r.rawDescription = r.rawDescription.replaceAll(compileText, "");
            } //I will make this good soon
            else if (r.rawDescription.contains("bronze:Compile")) {
                r.rawDescription = ""; // It's over!! If you only have Compile effects, you're gone!!!!!
            }
            r.initializeDescription();
        }
        return r;
    }
}