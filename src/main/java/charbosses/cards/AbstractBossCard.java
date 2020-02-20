package charbosses.cards;

import charbosses.bosses.AbstractCharBoss;
import charbosses.ui.EnemyEnergyPanel;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EvolvePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.MedicalKit;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class AbstractBossCard extends AbstractCard {

    public static final float HAND_SCALE = 0.55f;
    public static final float HOVER_SCALE = 0.9f;
    public static boolean recursionCheck = false;
    public AbstractCharBoss owner;
    public boolean hov2 = false;
    public int limit;
    protected int magicValue = 0;

    public AbstractBossCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                            CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.owner = AbstractCharBoss.boss;
        this.limit = 99;
        // TODO Auto-generated constructor stub
    }

    public AbstractBossCard(AbstractCard baseCard) {
        super("EvilWithinBossCard:" + baseCard.cardID, baseCard.name, baseCard.assetUrl, baseCard.cost, baseCard.rawDescription, baseCard.type,
                baseCard.color, baseCard.rarity, baseCard.target);
        this.owner = AbstractCharBoss.boss;
        this.limit = 99;
    }

    public static int statusValue() {
        int value = -5;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractCharBoss.boss != null) {
            if (AbstractCharBoss.boss.hasPower(EvolvePower.POWER_ID)) {
                value = 0;
            } else if (AbstractCharBoss.boss.hasRelic(MedicalKit.ID)) {
                value += 4;
            }
        }
        return value;
    }

    public int getPriority() {
        if (this.type == CardType.STATUS) {
            return -10;
        } else if (this.type == CardType.CURSE) {
            return -100;
        }
        return 1;
    }

    public int getValue() {
        if (this.type == CardType.STATUS) {
            return -10;
        } else if (this.type == CardType.CURSE) {
            return -100;
        }
        return (Math.max(this.damage, 0) + Math.max(this.block, 0) + this.magicNumber * this.magicValue) / Math.max(1, this.costForTurn) + (this.isEthereal ? 2 : 0);
    }

    public void applyPowers() {
        this.applyPowersToBlock();
        final AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        float tmp = (float) this.baseDamage;
        if (this.owner == null)
            this.owner = AbstractCharBoss.boss;
        if (this.owner.relics != null) {
            for (final AbstractRelic r : this.owner.relics) {
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int) tmp) {
                    this.isDamageModified = true;
                }
            }
        }
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
        }
        tmp = this.owner.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseDamage != (int) tmp) {
            this.isDamageModified = true;
        }
        for (final AbstractPower p : player.powers) {
            tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
        }
        tmp = player.stance.atDamageReceive(tmp, this.damageTypeForTurn);
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
        }
        for (final AbstractPower p : player.powers) {
            tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        if (this.baseDamage != MathUtils.floor(tmp)) {
            this.isDamageModified = true;
        }
        this.damage = MathUtils.floor(tmp);
        this.initializeDescription();
    }

    protected void applyPowersToBlock() {
        this.isBlockModified = false;
        float tmp = (float) this.baseBlock;
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.modifyBlock(tmp, this);
        }
        if (this.baseBlock != MathUtils.floor(tmp)) {
            this.isBlockModified = true;
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        this.block = MathUtils.floor(tmp);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        final AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        if (mo == null) {
            mo = this.owner;
        } else if (this.owner == null && mo instanceof AbstractCharBoss) {
            this.owner = (AbstractCharBoss) mo;
        }
        if (mo != null) {
            float tmp = (float) this.baseDamage;
            for (final AbstractRelic r : this.owner.relics) {
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int) tmp) {
                    this.isDamageModified = true;
                }
            }
            for (final AbstractPower p : this.owner.powers) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }
            tmp = this.owner.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int) tmp) {
                this.isDamageModified = true;
            }
            for (final AbstractPower p : player.powers) {
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }
            tmp = player.stance.atDamageReceive(tmp, this.damageTypeForTurn);
            for (final AbstractPower p : this.owner.powers) {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            }
            for (final AbstractPower p : player.powers) {
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }
            if (tmp < 0.0f) {
                tmp = 0.0f;
            }
            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }
            this.damage = MathUtils.floor(tmp);
        }
        this.initializeDescription();
    }

    public void triggerOnEndOfPlayerTurn() {
        if (this.isEthereal) {
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractCharBoss.boss.hand));
        }
    }

    public boolean hasEnoughEnergy() {
        for (final AbstractPower p : AbstractCharBoss.boss.powers) {
            if (!p.canPlayCard(this)) {
                this.cantUseMessage = AbstractCard.TEXT[13];
                return false;
            }
        }
        if (AbstractCharBoss.boss.hasPower("Entangled") && this.type == CardType.ATTACK) {
            this.cantUseMessage = AbstractCard.TEXT[10];
            return false;
        }
        for (final AbstractRelic r : AbstractCharBoss.boss.relics) {
            if (!r.canPlay(this)) {
                return false;
            }
        }
        for (final AbstractCard c : AbstractCharBoss.boss.hand.group) {
            if (!c.canPlay(this)) {
                return false;
            }
        }
        if (EnemyEnergyPanel.totalCount >= this.costForTurn || this.freeToPlay() || this.isInAutoplay) {
            return true;
        }
        this.cantUseMessage = AbstractCard.TEXT[11];
        return false;
    }

    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        if (m instanceof AbstractCharBoss) {
            return (this.type != CardType.STATUS || this.costForTurn >= -1 || (((AbstractCharBoss) m).hasRelic(MedicalKit.ID))) && (this.type != CardType.CURSE || this.costForTurn >= -1 || (((AbstractCharBoss) m).hasRelic(BlueCandle.ID))) && (this.cardPlayable(m) && this.hasEnoughEnergy());
        } else {
            return true;
        }
    }

    public boolean cardPlayable(final AbstractMonster m) {
        if (((this.target == CardTarget.ENEMY || this.target == CardTarget.SELF_AND_ENEMY) && AbstractDungeon.player != null && AbstractDungeon.player.isDying) || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.cantUseMessage = null;
            return false;
        }
        return true;
    }

    public void hover() {
        super.hover();
        if (!this.hov2) {
            this.hov2 = true;
            AbstractCharBoss.boss.hand.refreshHandLayout();
            this.targetDrawScale = AbstractBossCard.HOVER_SCALE;
            this.drawScale = AbstractBossCard.HOVER_SCALE;
        }
    }

    public void unhover() {
        super.unhover();
        if (this.hov2) {
            this.hov2 = false;
            AbstractCharBoss.boss.hand.refreshHandLayout();
            this.targetDrawScale = AbstractBossCard.HAND_SCALE;
        }
    }

    public int getUpgradeValue() {
        if (!this.canUpgrade()) {
            return -1;
        }
        AbstractBossCard uc = (AbstractBossCard) this.makeCopy();
        uc.upgrade();
        if (AbstractCharBoss.boss != null) {
            uc.owner = this.owner;
            uc.applyPowers();
        }
        return uc.getValue() - uc.getValue();
    }


}
