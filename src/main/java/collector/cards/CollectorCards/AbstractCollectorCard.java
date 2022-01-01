package collector.cards.CollectorCards;

import automaton.FunctionHelper;
import automaton.cardmods.EncodeMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import collector.CollectorChar;
import collector.TorchChar;
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
import static collector.CollectorChar.getTorchHead;

public abstract class AbstractCollectorCard extends CustomCard {
    public enum DTCardTarget {
        DEFAULT, DRAGON_ONLY, BOTH, FRONT, BACK
    }
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
    public String[] EXTENDED_DESCRIPTION;
    private AbstractCard functionPreviewCard;

    public boolean isCollectorSecondDamageModified;
    public int CollectorSecondDamage;
    public int BaseCollectorSecondDamage;
    public boolean upgradedCollectorSecondDamage;

    public static HashSet<String> playerPowerApplyToDragon;
    public static HashSet<String> relicApplyToDragon;

    public int douDamage = -1;
    public int douBaseDamage = -1;
    public boolean upgradedDuoDamage = false;
    public boolean isDuoDamageModified = false;

    public int douBlock = -1;
    public int douBaseBlock = -1;
    public boolean upgradedDuoBlock = false;
    public boolean isDuoBlockModified = false;

    public boolean frontDealsDmg = false;
    public boolean rearDealsDmg = false;

    public int[] dragonMultiDamage;

    public int douMagic = -1;
    public int douBaseMagic = -1;
    public boolean upgradedDuoMagic = false;
    public boolean isDuoMagicModified = false;
    public DTCardTarget dtCardTarget;
    static {
        playerPowerApplyToDragon = new HashSet<>();
        playerPowerApplyToDragon.add(VigorPower.POWER_ID);
        playerPowerApplyToDragon.add(PenNibPower.POWER_ID);
        relicApplyToDragon = new HashSet<>();
        relicApplyToDragon.add(WristBlade.ID);
        relicApplyToDragon.add(StrikeDummy.ID);
    }
    public AbstractCollectorCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, "ERROR", getCorrectPlaceholderImage(type, id),
                cost, "ERROR", type, CollectorChar.Enums.COLLECTOR, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = NAME = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }

    public AbstractCollectorCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
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
    public AbstractCollectorCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, DTCardTarget dtCardTarget) {
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

    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();

        TorchChar dragon = CollectorChar.getLivingTorchHead();
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

    public void upgradeTorchHeadDamage(int amount) {
        douBaseDamage += amount;
        upgradedDuoDamage = true;
    }

    public void upgradeTorchHeadBlock(int amount) {
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
    public static String makeID(String blah) {
        return "collector:" + blah;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedAuto) {
            auto = baseAuto;
            isAutoModified = true;
        }
        super.displayUpgrades();
        if (upgradedDuoDamage) {
            douDamage = douBaseDamage;
            isDuoDamageModified = true;
        }
        if (upgradedDuoBlock) {
            douBlock = douBaseBlock;
            isDuoBlockModified = true;
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
    @Override
    public void applyPowers()
    {
        TorchChar dragon = CollectorChar.getLivingTorchHead();
        if (douBaseDamage != -1) {
            douDamage= douBaseDamage;
            if (dragon != null) {
                isDuoDamageModified = false;
                if (!isMultiDamage) {
                    float tmp = (float) douBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToDragon.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToDragon.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        }
                    }
                    tmp = dragon.stance.atDamageGive(tmp, damageTypeForTurn, this);
                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToDragon.contains(p.ID)) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                    }

                    if (tmp < 0.0F) {
                        tmp = 0.0F;
                    }

                    douDamage = MathUtils.floor(tmp);

                    if (douBaseDamage != douDamage) {
                        isDuoDamageModified = true;
                    }
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) douBaseDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            if (relicApplyToDragon.contains(r.relicId)) {
                                tmp[i] = r.atDamageModify(tmp[i], this);
                            }
                        }
                        for (AbstractPower p : dragon.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToDragon.contains(p.ID)) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                        }
                        tmp[i] = dragon.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : dragon.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToDragon.contains(p.ID)) {
                                tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                            }
                        }
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        if (tmp[i] < 0.0F) {
                            tmp[i] = 0.0F;
                        }
                    }

                    dragonMultiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        dragonMultiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    douDamage = dragonMultiDamage[0];
                    if (douBaseDamage != douDamage) {
                        isDuoDamageModified = true;
                    }
                }
            }
        }
        CollectorSecondDamage = BaseCollectorSecondDamage;

        int tmp = baseDamage;
        baseDamage = BaseCollectorSecondDamage;

        super.applyPowers();

        CollectorSecondDamage = damage;
        baseDamage = tmp;

        super.applyPowers();

        isCollectorSecondDamageModified = (CollectorSecondDamage != BaseCollectorSecondDamage);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);

        TorchChar dragon = CollectorChar.getLivingTorchHead();
        if (douBaseDamage != -1) {
            douDamage = douBaseDamage;
            if (dragon != null) {
                isDuoDamageModified = false;
                if (!isMultiDamage && mo != null) {
                    float tmp = (float) douBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToDragon.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        if (douBaseDamage != (int) tmp) {
                            isDuoDamageModified = true;
                        }
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToDragon.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (douBaseDamage != (int) tmp) {
                                isDuoDamageModified= true;
                            }
                        }
                    }
                    tmp = dragon.stance.atDamageGive(tmp, damageTypeForTurn, this);
                    for (AbstractPower p : mo.powers) {
                        tmp = p.atDamageReceive(tmp, damageTypeForTurn, this);
                    }

                    for (AbstractPower p : dragon.powers) {
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

                    douDamage = MathUtils.floor(tmp);
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) douDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            if (relicApplyToDragon.contains(r.relicId)) {
                                tmp[i] = r.atDamageModify(tmp[i], this);
                            }
                        }

                        for (AbstractPower p : dragon.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToDragon.contains(p.ID)) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                        }
                        tmp[i] = dragon.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : m.get(i).powers) {
                            tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                        }

                        for (AbstractPower p : dragon.powers) {
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

                    dragonMultiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        dragonMultiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    douDamage = dragonMultiDamage[0];
                }
                if (douBaseDamage != douDamage) {
                    isDuoDamageModified = true;
                }
            }
        }
        CollectorSecondDamage = BaseCollectorSecondDamage;

        int tmp = baseDamage;
        baseDamage = BaseCollectorSecondDamage;

        super.calculateCardDamage(mo);

        CollectorSecondDamage = damage;
        baseDamage = tmp;

        super.calculateCardDamage(mo);

        isCollectorSecondDamageModified = (CollectorSecondDamage != BaseCollectorSecondDamage);
    }
    public void upgradeCollectorSecondDamage(int amount) {
        BaseCollectorSecondDamage += amount;
        CollectorSecondDamage = BaseCollectorSecondDamage;
        isCollectorSecondDamageModified = true;
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

    public void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx, AbstractGameAction.AttackEffect fxTorch) {
        if (frontDealsDmg) {
            if (CollectorChar.isFrontTorchHead()) {
                atb(new DamageAction(m, new DamageInfo(getTorchHead(), douDamage, DamageInfo.DamageType.NORMAL), fxTorch));
            } else atb(new DamageAction(m, makeInfo(), fx));
        }
        if (rearDealsDmg) {
            if (!CollectorChar.isFrontTorchHead()) {
                atb(new DamageAction(m, new DamageInfo(getTorchHead(), douDamage, DamageInfo.DamageType.NORMAL), fxTorch));
            } else atb(new DamageAction(m, makeInfo(), fx));
        }
        if (!frontDealsDmg && !rearDealsDmg){
            atb(new DamageAction(m, makeInfo(), fx));
        }

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

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if (card instanceof AbstractCollectorCard) {
            AbstractCollectorCard dtCard = (AbstractCollectorCard) card;
            dtCard.douBaseDamage = douBaseDamage;
            dtCard.douBaseBlock = douBaseBlock;
        }
        return card;
    }
}