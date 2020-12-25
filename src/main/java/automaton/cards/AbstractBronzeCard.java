package automaton.cards;

import automaton.AutomatonChar;
import automaton.FunctionHelper;
import automaton.cardmods.CardEffectsCardMod;
import automaton.cardmods.EncodeMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
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
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.ArrayList;

import static automaton.AutomatonMod.getModID;
import static automaton.AutomatonMod.makeCardPath;

public abstract class AbstractBronzeCard extends CustomCard {

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected String DESCRIPTION;
    protected String UPGRADE_DESCRIPTION;
    protected String[] EXTENDED_DESCRIPTION;

    public int auto;
    public int baseAuto;
    public boolean upgradedAuto;
    public boolean isAutoModified;

    private AbstractCard functionPreviewCard;
    private static float functionPreviewCardScale = .9f;
    private static float functionPreviewCardY = Settings.HEIGHT * 0.45F;
    private static float functionPreviewCardX = Settings.WIDTH * 0.1F;

    public boolean isHoveredInSequence = false;

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

    public int position = -1;

    public boolean doSpecialCompileStuff = true;

    public AbstractBronzeCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, AutomatonChar.Enums.BRONZE_AUTOMATON, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractBronzeCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public void onCompile(AbstractCard function, boolean forGameplay) {
        // Called when the function is about to be created. Watch out, onCompile() is called in order of insertion.
        if (forGameplay) {
            CardModifierManager.removeModifiersById(this, EncodeMod.ID, true);
        }
        CardModifierManager.addModifier(function, new CardEffectsCardMod(this));
    }

    public void onCompileToChangeCost(AbstractCard function, boolean forGameplay) {
        // Called after all cards have done onCompile, before relics and powers have.

    }

    public void onInput() {
        // Called when the card is about to enter the Sequence. This could be deleted if it stays unused.
    }

    public String getNoun() {
        return EXTENDED_DESCRIPTION[0];
    }

    public String getAdjective() {
        return EXTENDED_DESCRIPTION[1];
    }

    public boolean onOutput() {
        // Called when the function is about to be added to the hand,
        // before the function is created.
        // Return true to continue adding to hand. Return false to stop that.
        // Not the best hook. Should maybe find a way to not use this.
        return true;
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
        return position == FunctionHelper.held.size() - 1;
    }

    boolean firstCard() {
        return position == 0;
    }
}