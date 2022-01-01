package collector.cards.CollectorCards;

import basemod.abstracts.CustomCard;
import collector.CollectorChar;
import collector.TorchChar;
import com.badlogic.gdx.Gdx;
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
import static collector.CollectorChar.getTorchHead;
import static collector.CollectorChar.isFrontTorchHead;

public abstract class AbstractCollectorCard extends CustomCard {
    public enum CollectorCardSource {
        DEFAULT, TORCH_ONLY, BOTH, FRONT, BACK
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

    public static HashSet<String> playerPowerApplyToTorch;
    public static HashSet<String> relicApplyToTorch;

    public int douDamage = -1;
    public int douBaseDamage = -1;
    public boolean upgradedDuoDamage = false;
    public boolean isDuoDamageModified = false;

    public int douBlock = -1;
    public int douBaseBlock = -1;
    public boolean upgradedDuoBlock = false;
    public boolean isDuoBlockModified = false;

    public int FrontDamage = -1;
    public int FrontBaseDamage = -1;
    public boolean upgradedFrontDamage = false;
    public boolean isFrontDamageModified = false;

    public int FrontBlock = -1;
    public int FrontBaseBlock = -1;
    public boolean upgradedFrontBlock = false;
    public boolean isFrontBlockModified = false;

    public int RearDamage = -1;
    public int RearBaseDamage = -1;
    public boolean upgradedRearDamage = false;
    public boolean isRearDamageModified = false;

    public int RearBlock = -1;
    public int RearBaseBlock = -1;
    public boolean upgradedRearBlock = false;
    public boolean isRearBlockModified = false;

    public boolean frontDealsDmg = false;
    public boolean rearDealsDmg = false;

    public boolean frontGainsBlock = false;
    public boolean rearGainsBlock = false;

    public int[] TorchMultiDamage;

    public int douMagic = -1;
    public int douBaseMagic = -1;
    public boolean upgradedDuoMagic = false;
    public boolean isDuoMagicModified = false;
    public CollectorCardSource DamageSource;
    public CollectorCardSource BlockSource;
    static {
        playerPowerApplyToTorch = new HashSet<>();
        playerPowerApplyToTorch.add(VigorPower.POWER_ID);
        playerPowerApplyToTorch.add(PenNibPower.POWER_ID);
        relicApplyToTorch = new HashSet<>();
        relicApplyToTorch.add(WristBlade.ID);
        relicApplyToTorch.add(StrikeDummy.ID);
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
    public AbstractCollectorCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, CollectorCardSource DamageSource, CollectorCardSource BlockSource) {
        this(id,cost, type, rarity, target);
        this.DamageSource = DamageSource;
        this.BlockSource = BlockSource;
        SetPositionalVarsFromEnum();
    }
    public AbstractCollectorCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, CollectorCardSource DamageSource) {
        this(id,cost, type, rarity, target);
        this.BlockSource = this.DamageSource = DamageSource;
        SetPositionalVarsFromEnum();
    }
    public void SetPositionalVarsFromEnum(){
        if (DamageSource == CollectorCardSource.FRONT){
            frontDealsDmg = true;
        }
        if (DamageSource == CollectorCardSource.BACK){
            rearDealsDmg = true;
        }
        if (BlockSource == CollectorCardSource.FRONT){
            frontGainsBlock = true;
        }
        if (BlockSource == CollectorCardSource.BACK){
            rearGainsBlock = true;
        }
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
        if (BlockSource == CollectorCardSource.FRONT && FrontBlock != -1) {
            FrontBlock = FrontBaseBlock;
            if (dragon != null && isFrontTorchHead()) {
                isFrontBlockModified = false;
                float tmp = (float) FrontBaseBlock;

                for (AbstractPower p : dragon.powers) {
                    tmp = p.modifyBlock(tmp);
                    if (FrontBaseBlock != MathUtils.floor(tmp)) {
                        isDuoBlockModified = true;
                    }
                }

                if (tmp < 0.0F) {
                    tmp = 0.0F;
                }

                FrontBlock = MathUtils.floor(tmp);
            } else {
                isFrontBlockModified = false;
                float tmp = (float) FrontBaseBlock;

                for (AbstractPower p : AbstractDungeon.player.powers) {
                    tmp = p.modifyBlock(tmp);
                    if (FrontBaseBlock != MathUtils.floor(tmp)) {
                        isFrontBlockModified = true;
                    }
                }

                if (tmp < 0.0F) {
                    tmp = 0.0F;
                }

                FrontBlock = MathUtils.floor(tmp);
            }
        }
        if (BlockSource == CollectorCardSource.BACK && RearBlock != -1) {
            RearBlock = FrontBaseBlock;
            if (dragon != null && isFrontTorchHead()) {
                isRearBlockModified = false;
                float tmp = (float) RearBaseBlock;

                for (AbstractPower p : dragon.powers) {
                    tmp = p.modifyBlock(tmp);
                    if (RearBaseBlock != MathUtils.floor(tmp)) {
                        isRearBlockModified = true;
                    }
                }

                if (tmp < 0.0F) {
                    tmp = 0.0F;
                }

                RearBlock = MathUtils.floor(tmp);
            } else {
                isRearBlockModified = false;
                float tmp = (float) RearBaseBlock;

                for (AbstractPower p : AbstractDungeon.player.powers) {
                    tmp = p.modifyBlock(tmp);
                    if (RearBaseBlock != MathUtils.floor(tmp)) {
                        isRearBlockModified = true;
                    }
                }

                if (tmp < 0.0F) {
                    tmp = 0.0F;
                }

                RearBlock = MathUtils.floor(tmp);
            }
        }
    }



    public int calculateCardDamageAsMonster(AbstractCreature attacker, int[] baseDamage, AbstractMonster mo, int[] enemyMultiDamage) {
        if (!isMultiDamage && mo != null) {
            float tmp = baseDamage[0];

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (relicApplyToTorch.contains(r.relicId)) {
                    tmp = r.atDamageModify(tmp, this);
                }
            }

            for (AbstractPower p : attacker.powers) {
                tmp = p.atDamageGive(tmp, damageTypeForTurn);
            }
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (playerPowerApplyToTorch.contains(p.ID)) {
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
                if (playerPowerApplyToTorch.contains(p.ID)) {
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
                    if (relicApplyToTorch.contains(r.relicId)) {
                        tmp[i] = r.atDamageModify(tmp[i], this);
                    }
                }

                for (AbstractPower p : attacker.powers) {
                    tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                }
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (playerPowerApplyToTorch.contains(p.ID)) {
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
                    if (playerPowerApplyToTorch.contains(p.ID)) {
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
        isFrontBlockModified = false;
        isFrontDamageModified = false;
        FrontBlock = douBaseBlock;
        FrontDamage = douBaseDamage;
        isRearBlockModified = false;
        isRearDamageModified = false;
        RearBlock = douBaseBlock;
        RearDamage = douBaseDamage;
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
        if (upgradedFrontDamage) {
            FrontDamage = FrontBaseDamage;
            isFrontDamageModified = true;
        }
        if (upgradedFrontBlock) {
            FrontBlock = FrontBaseBlock;
            isFrontBlockModified = true;
        }
        if (upgradedRearDamage) {
            RearDamage = RearBaseDamage;
            isRearDamageModified = true;
        }
        if (upgradedRearBlock) {
            RearBlock = RearBaseBlock;
            isRearBlockModified = true;
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
        if (frontDealsDmg && FrontDamage != -1){
            FrontDamage = FrontBaseDamage;
            if (CollectorChar.isFrontTorchHead()){
                if (dragon != null) {
                    isFrontDamageModified = false;
                    if (!isMultiDamage) {
                        float tmp = (float) FrontBaseDamage;

                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            if (relicApplyToTorch.contains(r.relicId)) {
                                tmp = r.atDamageModify(tmp, this);
                            }
                        }

                        for (AbstractPower p : dragon.powers) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (FrontBaseDamage != (int) tmp) {
                                isFrontDamageModified = true;
                            }
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp = p.atDamageGive(tmp, damageTypeForTurn);
                                if (FrontBaseDamage != (int) tmp) {
                                    isFrontDamageModified= true;
                                }
                            }
                        }
                        tmp = dragon.stance.atDamageGive(tmp, damageTypeForTurn, this);

                        for (AbstractPower p : dragon.powers) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                            }
                        }
                        if (tmp < 0.0F) {
                            tmp = 0.0F;
                        }

                        FrontDamage = MathUtils.floor(tmp);
                    } else {
                        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                        float[] tmp = new float[m.size()];

                        int i;
                        for (i = 0; i < tmp.length; ++i) {
                            tmp[i] = (float) FrontDamage;
                        }

                        for (i = 0; i < tmp.length; ++i) {
                            for (AbstractRelic r : AbstractDungeon.player.relics) {
                                tmp[i] = r.atDamageModify(tmp[i], this);
                            }

                            for (AbstractPower p : AbstractDungeon.player.powers) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                            for (AbstractPower p : AbstractDungeon.player.powers) {
                                if (playerPowerApplyToTorch.contains(p.ID)) {
                                    tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                                }
                            }
                            tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                            for (AbstractPower p : m.get(i).powers) {
                                tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                            }

                            for (AbstractPower p : AbstractDungeon.player.powers) {
                                tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                            }
                            for (AbstractPower p : AbstractDungeon.player.powers) {
                                if (playerPowerApplyToTorch.contains(p.ID)) {
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

                        TorchMultiDamage = new int[tmp.length];

                        for (i = 0; i < tmp.length; ++i) {
                            TorchMultiDamage[i] = MathUtils.floor(tmp[i]);
                        }

                        FrontDamage = TorchMultiDamage[0];
                    }
                    if (FrontBaseDamage != FrontDamage) {
                        isFrontDamageModified = true;
                    }
                }
            } else {
                isFrontDamageModified = false;
                if (!isMultiDamage) {
                    float tmp = (float) FrontBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        if (FrontBaseDamage != (int) tmp) {
                            isFrontDamageModified = true;
                        }
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (FrontBaseDamage != (int) tmp) {
                                isFrontDamageModified= true;
                            }
                        }
                    }
                    tmp = dragon.stance.atDamageGive(tmp, damageTypeForTurn, this);

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                    }
                    if (tmp < 0.0F) {
                        tmp = 0.0F;
                    }

                    FrontDamage = MathUtils.floor(tmp);
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) FrontDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            tmp[i] = r.atDamageModify(tmp[i], this);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : m.get(i).powers) {
                            tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
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

                    multiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        multiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    FrontDamage = multiDamage[0];
                }
                if (FrontBaseDamage != FrontDamage) {
                    isFrontDamageModified = true;
                }
            }
        }
        if (rearDealsDmg && RearDamage != -1){
            RearDamage = RearBaseDamage;
            if (CollectorChar.isFrontTorchHead()){
                if (dragon != null) {
                    isRearDamageModified = false;
                    if (!isMultiDamage) {
                        float tmp = (float) RearBaseDamage;

                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            if (relicApplyToTorch.contains(r.relicId)) {
                                tmp = r.atDamageModify(tmp, this);
                            }
                        }

                        for (AbstractPower p : dragon.powers) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (RearBaseDamage != (int) tmp) {
                                isFrontDamageModified = true;
                            }
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp = p.atDamageGive(tmp, damageTypeForTurn);
                                if (RearBaseDamage != (int) tmp) {
                                    isRearDamageModified= true;
                                }
                            }
                        }
                        tmp = dragon.stance.atDamageGive(tmp, damageTypeForTurn, this);

                        for (AbstractPower p : dragon.powers) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                            }
                        }
                        if (tmp < 0.0F) {
                            tmp = 0.0F;
                        }

                        RearDamage = MathUtils.floor(tmp);
                    } else {
                        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                        float[] tmp = new float[m.size()];

                        int i;
                        for (i = 0; i < tmp.length; ++i) {
                            tmp[i] = (float) RearDamage;
                        }

                        for (i = 0; i < tmp.length; ++i) {
                            for (AbstractRelic r : AbstractDungeon.player.relics) {
                                if (relicApplyToTorch.contains(r.relicId)) {
                                    tmp[i] = r.atDamageModify(tmp[i], this);
                                }
                            }

                            for (AbstractPower p : dragon.powers) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                            for (AbstractPower p : AbstractDungeon.player.powers) {
                                if (playerPowerApplyToTorch.contains(p.ID)) {
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
                                if (playerPowerApplyToTorch.contains(p.ID)) {
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

                        TorchMultiDamage = new int[tmp.length];

                        for (i = 0; i < tmp.length; ++i) {
                            TorchMultiDamage[i] = MathUtils.floor(tmp[i]);
                        }

                        FrontDamage = TorchMultiDamage[0];
                    }
                    if (FrontBaseDamage != FrontDamage) {
                        isFrontDamageModified = true;
                    }
                }
            } else {
                isFrontDamageModified = false;
                if (!isMultiDamage) {
                    float tmp = (float) RearBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        if (RearBaseDamage != (int) tmp) {
                            isRearDamageModified = true;
                        }
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (RearBaseDamage != (int) tmp) {
                                isRearDamageModified= true;
                            }
                        }
                    }
                    tmp = AbstractDungeon.player.stance.atDamageGive(tmp, damageTypeForTurn, this);

                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                    }
                    if (tmp < 0.0F) {
                        tmp = 0.0F;
                    }

                    RearDamage = MathUtils.floor(tmp);
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) RearDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            tmp[i] = r.atDamageModify(tmp[i], this);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : m.get(i).powers) {
                            tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
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

                    multiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        multiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    RearDamage = multiDamage[0];
                }
                if (RearBaseDamage != RearDamage) {
                    isRearDamageModified = true;
                }
            }
        }
        if (douBaseDamage != -1) {
            douDamage= douBaseDamage;
            if (dragon != null) {
                isDuoDamageModified = false;
                if (!isMultiDamage) {
                    float tmp = (float) douBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        }
                    }
                    tmp = dragon.stance.atDamageGive(tmp, damageTypeForTurn, this);
                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
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
                            if (relicApplyToTorch.contains(r.relicId)) {
                                tmp[i] = r.atDamageModify(tmp[i], this);
                            }
                        }
                        for (AbstractPower p : dragon.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                        }
                        tmp[i] = dragon.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : dragon.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                            }
                        }
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        if (tmp[i] < 0.0F) {
                            tmp[i] = 0.0F;
                        }
                    }

                    TorchMultiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        TorchMultiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    douDamage = TorchMultiDamage[0];
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

        if (frontDealsDmg && FrontDamage != -1){
            FrontDamage = FrontBaseDamage;
            if (CollectorChar.isFrontTorchHead()){
            if (dragon != null) {
                isFrontDamageModified = false;
                if (!isMultiDamage && mo != null) {
                    float tmp = (float) FrontBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        if (FrontBaseDamage != (int) tmp) {
                            isFrontDamageModified = true;
                        }
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (FrontBaseDamage != (int) tmp) {
                                isFrontDamageModified= true;
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
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                    }
                    for (AbstractPower p : mo.powers) {
                        tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
                    }
                    if (tmp < 0.0F) {
                        tmp = 0.0F;
                    }

                    FrontDamage = MathUtils.floor(tmp);
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) FrontDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            tmp[i] = r.atDamageModify(tmp[i], this);
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                        }
                        tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : m.get(i).powers) {
                            tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
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

                    TorchMultiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        TorchMultiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    FrontDamage = TorchMultiDamage[0];
                }
                if (FrontBaseDamage != FrontDamage) {
                    isFrontDamageModified = true;
                }
            }
            } else {
                isFrontDamageModified = false;
                if (!isMultiDamage && mo != null) {
                    float tmp = (float) FrontBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : dragon.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        if (FrontBaseDamage != (int) tmp) {
                            isFrontDamageModified = true;
                        }
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (FrontBaseDamage != (int) tmp) {
                                isFrontDamageModified= true;
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
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                    }
                    for (AbstractPower p : mo.powers) {
                        tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
                    }
                    if (tmp < 0.0F) {
                        tmp = 0.0F;
                    }

                    FrontDamage = MathUtils.floor(tmp);
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) FrontDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            tmp[i] = r.atDamageModify(tmp[i], this);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : m.get(i).powers) {
                            tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
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

                    multiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        multiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    FrontDamage = multiDamage[0];
                }
                if (FrontBaseDamage != FrontDamage) {
                    isFrontDamageModified = true;
                }
            }
        }
        if (rearDealsDmg && RearDamage != -1){
            RearDamage = RearBaseDamage;
            if (CollectorChar.isFrontTorchHead()){
                if (dragon != null) {
                    isRearDamageModified = false;
                    if (!isMultiDamage && mo != null) {
                        float tmp = (float) RearBaseDamage;

                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            if (relicApplyToTorch.contains(r.relicId)) {
                                tmp = r.atDamageModify(tmp, this);
                            }
                        }

                        for (AbstractPower p : dragon.powers) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (RearBaseDamage != (int) tmp) {
                                isFrontDamageModified = true;
                            }
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp = p.atDamageGive(tmp, damageTypeForTurn);
                                if (RearBaseDamage != (int) tmp) {
                                    isRearDamageModified= true;
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
                            if (playerPowerApplyToTorch.contains(p.ID)) {
                                tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                            }
                        }
                        for (AbstractPower p : mo.powers) {
                            tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
                        }
                        if (tmp < 0.0F) {
                            tmp = 0.0F;
                        }

                        RearDamage = MathUtils.floor(tmp);
                    } else {
                        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                        float[] tmp = new float[m.size()];

                        int i;
                        for (i = 0; i < tmp.length; ++i) {
                            tmp[i] = (float) RearDamage;
                        }

                        for (i = 0; i < tmp.length; ++i) {
                            for (AbstractRelic r : AbstractDungeon.player.relics) {
                                if (relicApplyToTorch.contains(r.relicId)) {
                                    tmp[i] = r.atDamageModify(tmp[i], this);
                                }
                            }

                            for (AbstractPower p : dragon.powers) {
                                tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                            }
                            for (AbstractPower p : AbstractDungeon.player.powers) {
                                if (playerPowerApplyToTorch.contains(p.ID)) {
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
                                if (playerPowerApplyToTorch.contains(p.ID)) {
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

                        TorchMultiDamage = new int[tmp.length];

                        for (i = 0; i < tmp.length; ++i) {
                            TorchMultiDamage[i] = MathUtils.floor(tmp[i]);
                        }

                        FrontDamage = TorchMultiDamage[0];
                    }
                    if (FrontBaseDamage != FrontDamage) {
                        isFrontDamageModified = true;
                    }
                }
            } else {
                isFrontDamageModified = false;
                if (!isMultiDamage && mo != null) {
                    float tmp = (float) RearBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
                            tmp = r.atDamageModify(tmp, this);
                        }
                    }

                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        tmp = p.atDamageGive(tmp, damageTypeForTurn);
                        if (RearBaseDamage != (int) tmp) {
                            isRearDamageModified = true;
                        }
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageGive(tmp, damageTypeForTurn);
                            if (RearBaseDamage != (int) tmp) {
                                isRearDamageModified= true;
                            }
                        }
                    }
                    tmp = AbstractDungeon.player.stance.atDamageGive(tmp, damageTypeForTurn, this);
                    for (AbstractPower p : mo.powers) {
                        tmp = p.atDamageReceive(tmp, damageTypeForTurn, this);
                    }

                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                    }
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (playerPowerApplyToTorch.contains(p.ID)) {
                            tmp = p.atDamageFinalGive(tmp, damageTypeForTurn);
                        }
                    }
                    for (AbstractPower p : mo.powers) {
                        tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn, this);
                    }
                    if (tmp < 0.0F) {
                        tmp = 0.0F;
                    }

                    RearDamage = MathUtils.floor(tmp);
                } else {
                    ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
                    float[] tmp = new float[m.size()];

                    int i;
                    for (i = 0; i < tmp.length; ++i) {
                        tmp[i] = (float) RearDamage;
                    }

                    for (i = 0; i < tmp.length; ++i) {
                        for (AbstractRelic r : AbstractDungeon.player.relics) {
                            tmp[i] = r.atDamageModify(tmp[i], this);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        tmp[i] = AbstractDungeon.player.stance.atDamageGive(tmp[i], damageTypeForTurn, this);
                        for (AbstractPower p : m.get(i).powers) {
                            tmp[i] = p.atDamageReceive(tmp[i], damageTypeForTurn, this);
                        }

                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            tmp[i] = p.atDamageFinalGive(tmp[i], damageTypeForTurn);
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

                    multiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        multiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    RearDamage = multiDamage[0];
                }
                if (RearBaseDamage != RearDamage) {
                    isRearDamageModified = true;
                }
            }
        }
        if (douBaseDamage != -1) {
            douDamage = douBaseDamage;
            if (dragon != null) {
                isDuoDamageModified = false;
                if (!isMultiDamage && mo != null) {
                    float tmp = (float) douBaseDamage;

                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (relicApplyToTorch.contains(r.relicId)) {
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
                        if (playerPowerApplyToTorch.contains(p.ID)) {
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
                        if (playerPowerApplyToTorch.contains(p.ID)) {
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
                            if (relicApplyToTorch.contains(r.relicId)) {
                                tmp[i] = r.atDamageModify(tmp[i], this);
                            }
                        }

                        for (AbstractPower p : dragon.powers) {
                            tmp[i] = p.atDamageGive(tmp[i], damageTypeForTurn);
                        }
                        for (AbstractPower p : AbstractDungeon.player.powers) {
                            if (playerPowerApplyToTorch.contains(p.ID)) {
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
                            if (playerPowerApplyToTorch.contains(p.ID)) {
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

                    TorchMultiDamage = new int[tmp.length];

                    for (i = 0; i < tmp.length; ++i) {
                        TorchMultiDamage[i] = MathUtils.floor(tmp[i]);
                    }

                    douDamage = TorchMultiDamage[0];
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
    public void upgradeFrontDamage(int amount) {
        FrontBaseDamage += amount;
        FrontDamage = FrontBaseDamage;
        isFrontDamageModified = true;
    }
    public void upgradeFrontBlock(int amount) {
        FrontBaseBlock += amount;
        FrontBlock = FrontBaseBlock;
        isFrontBlockModified = true;
    }
    public void upgradeRearDamage(int amount) {
        RearBaseDamage += amount;
        RearDamage = RearBaseDamage;
        isRearDamageModified = true;
    }
    public void upgradeRearBlock(int amount) {
        RearBaseBlock += amount;
        RearBlock = RearBaseBlock;
        isRearBlockModified = true;
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
                atb(new DamageAction(m, new DamageInfo(getTorchHead(), FrontDamage, DamageInfo.DamageType.NORMAL), fxTorch));
            } else atb(new DamageAction(m, makeInfo(), fx));
        }
        if (rearDealsDmg) {
            if (!CollectorChar.isFrontTorchHead()) {
                atb(new DamageAction(m, new DamageInfo(getTorchHead(), RearDamage, DamageInfo.DamageType.NORMAL), fxTorch));
            } else atb(new DamageAction(m, makeInfo(), fx));
        }
        if (!frontDealsDmg && !rearDealsDmg){
            atb(new DamageAction(m, makeInfo(), fx));
        }
    }
    public void TorchDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(getTorchHead(), douDamage, DamageInfo.DamageType.NORMAL), fx));
    }
    public void CollectorDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, makeInfo(), fx));
    }
    public void allDmg(AbstractGameAction.AttackEffect fx, AbstractGameAction.AttackEffect fxTorch) {
        if (frontDealsDmg) {
            if (CollectorChar.isFrontTorchHead()) {
                atb(new DamageAllEnemiesAction(getTorchHead(), TorchMultiDamage, damageTypeForTurn, fxTorch));
            } else  atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
        }
        if (rearDealsDmg) {
            if (!CollectorChar.isFrontTorchHead()) {
                atb(new DamageAllEnemiesAction(getTorchHead(), TorchMultiDamage, damageTypeForTurn, fx));
            } else  atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fxTorch));
        }
        if (!frontDealsDmg && !rearDealsDmg){
            atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
        }
    }
    public void TorchallDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(getTorchHead(), TorchMultiDamage, damageTypeForTurn, fx));
    }
    public void CollectorallDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }
    public void blck() {
        if (frontGainsBlock) {
            if (CollectorChar.isFrontTorchHead()) {
                atb(new GainBlockAction(getTorchHead(), AbstractDungeon.player, douBlock));
            } else  atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        }
        if (rearGainsBlock) {
            if (!CollectorChar.isFrontTorchHead()) {
                atb(new GainBlockAction(getTorchHead(), AbstractDungeon.player, douBlock));
            } else atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        }
        if (!frontGainsBlock && !rearGainsBlock){
            atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        }
    }
    public void Torchblck() {
        atb(new GainBlockAction(getTorchHead(), AbstractDungeon.player, douBlock));
    }
    public void Collectorblck() {
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