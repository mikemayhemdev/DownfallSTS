package collector.cards.Collectibles;

import automaton.FunctionHelper;
import automaton.cardmods.EncodeMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import collector.CollectorChar;
import collector.TorchChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.patches.CollectibleCardColorEnumPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.StrikeDummy;
import com.megacrit.cardcrawl.relics.WristBlade;

import java.util.ArrayList;
import java.util.HashSet;

import static automaton.AutomatonMod.makeCardPath;
import static automaton.FunctionHelper.WITH_DELIMITER;
import static collector.cards.CollectorCards.AbstractCollectorCard.playerPowerApplyToDragon;
import static collector.cards.CollectorCards.AbstractCollectorCard.relicApplyToDragon;

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
    public int douDamage = -1;
    public int douBaseDamage = -1;
    public boolean upgradedDuoDamage = false;
    public boolean isDuoDamageModified = false;

    public int douBlock = -1;
    public int douBaseBlock = -1;
    public boolean upgradedDuoBlock = false;
    public boolean isDuoBlockModified = false;

    public int[] dragonMultiDamage;

    public int douMagic = -1;
    public int douBaseMagic = -1;
    public boolean upgradedDuoMagic = false;
    public boolean isDuoMagicModified = false;
    public AbstractCollectorCard.DTCardTarget dtCardTarget;
    static {
        playerPowerApplyToDragon = new HashSet<>();
        playerPowerApplyToDragon.add(VigorPower.POWER_ID);
        playerPowerApplyToDragon.add(PenNibPower.POWER_ID);
        relicApplyToDragon = new HashSet<>();
        relicApplyToDragon.add(WristBlade.ID);
        relicApplyToDragon.add(StrikeDummy.ID);
    }
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
    public AbstractCollectibleCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, AbstractCollectorCard.DTCardTarget dtCardTarget) {
        this(id,cost, type, rarity, target);
        this.dtCardTarget = dtCardTarget;
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

    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();

        TorchChar dragon = CollectorChar.getLivingDragon();
        if (douBaseBlock != -1) {
            douBlock = douBaseBlock;
            if (dragon != null) {
                isDuoBlockModified = false;
                float tmp = (float) douBaseBlock;

                for (AbstractPower p : dragon.powers) {
                    tmp = p.modifyBlock(tmp);
                    if (douBaseBlock != MathUtils.floor(tmp)) {
                        isDuoBlockModified = true;
                    }
                }

                if (tmp < 0.0F) {
                    tmp = 0.0F;
                }

                douBlock = MathUtils.floor(tmp);
            }
        }
    }



    public int calculateCardDamageAsMonster(AbstractCreature attacker, int[] baseDamage, AbstractMonster mo, int[] enemyMultiDamage) {
        if (!isMultiDamage && mo != null) {
            float tmp = baseDamage[0];

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (relicApplyToDragon.contains(r.relicId)) {
                    tmp = r.atDamageModify(tmp, this);
                }
            }

            for (AbstractPower p : attacker.powers) {
                tmp = p.atDamageGive(tmp, damageTypeForTurn);
            }
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (playerPowerApplyToDragon.contains(p.ID)) {
                    tmp = p.atDamageGive(tmp, damageTypeForTurn);
                }
            }

            for (AbstractPower p : mo.powers) {
                tmp = p.atDamageReceive(tmp, damageTypeForTurn, this);
            }

            for (AbstractPower p : attacker.powers) {
                tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
            }
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (playerPowerApplyToDragon.contains(p.ID)) {
                    tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                }
            }
            for (AbstractPower p : mo.powers) {
                tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
            }
            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            return MathUtils.floor(tmp);
        } else {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];

            int i;
            for (i = 0; i < tmp.length; ++i) {
                tmp[i] = baseDamage[0];
            }

            for (i = 0; i < tmp.length; ++i) {
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (relicApplyToDragon.contains(r.relicId)) {
                        tmp[i] = r.atDamageModify(tmp[i], this);
                    }
                }

                for (AbstractPower p : attacker.powers) {
                    tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                }
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (playerPowerApplyToDragon.contains(p.ID)) {
                        tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                    }
                }
                for (AbstractPower p : m.get(i).powers) {
                    tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                }

                for (AbstractPower p : attacker.powers) {
                    tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                }
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (playerPowerApplyToDragon.contains(p.ID)) {
                        tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                    }
                }
                for (AbstractPower p : m.get(i).powers) {
                    tmp[i] = p.atDamageFinalReceive(tmp[i], damageTypeForTurn, this);
                }
            }

            for (i = 0; i < tmp.length; ++i) {
                if (tmp[i] < 0.0F) {
                    tmp[i] = 0.0F;
                }
            }

            if (enemyMultiDamage != null && enemyMultiDamage.length > 0) {

                for (i = 0; i < tmp.length && i < enemyMultiDamage.length; ++i) {
                    enemyMultiDamage[i] = MathUtils.floor(tmp[i]);
                }

                return enemyMultiDamage[0];
            } else {
                return (int) tmp[0];
            }
        }
    }

    public void upgradeDTDragonDamage(int amount) {
        douBaseDamage += amount;
        upgradedDuoDamage = true;
    }

    public void upgradeDTDragonBlock(int amount) {
        douBaseBlock += amount;
        upgradedDuoBlock = true;
    }


    @Override
    public void resetAttributes() {
        super.resetAttributes();
        auto = baseAuto;
        isAutoModified = false;
        isDuoBlockModified = false;
        isDuoDamageModified = false;
        douBlock = douBaseBlock;
        douDamage = douBaseDamage;
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