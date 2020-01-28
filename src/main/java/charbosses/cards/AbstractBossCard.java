package charbosses.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.MedicalKit;

import basemod.abstracts.CustomCard;
import charbosses.bosses.AbstractCharBoss;
import charbosses.ui.EnemyEnergyPanel;

public abstract class AbstractBossCard extends CustomCard {

	
	public AbstractCharBoss owner;
	
	public AbstractBossCard(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
		this.owner = AbstractCharBoss.boss;
		// TODO Auto-generated constructor stub
	}
	
	public AbstractBossCard(AbstractCard baseCard) {
		super("EvilWithinBossCard:" + baseCard.cardID, baseCard.name, baseCard.assetUrl, baseCard.cost, baseCard.rawDescription, baseCard.type, 
				baseCard.color, baseCard.rarity, baseCard.target);
		this.owner = AbstractCharBoss.boss;
	}
	
	public int getPriority() {
		return 1;
	}
	
	protected void applyPowersToBlock() {
        this.isBlockModified = false;
        float tmp = (float)this.baseBlock;
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
        	this.owner = (AbstractCharBoss)mo;
        }
        if (mo != null) {
            float tmp = (float)this.baseDamage;
            for (final AbstractRelic r : this.owner.relics) {
                tmp = r.atDamageModify(tmp, this);
                if (this.baseDamage != (int)tmp) {
                    this.isDamageModified = true;
                }
            }
            for (final AbstractPower p : this.owner.powers) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }
            tmp = this.owner.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
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
    		return (this.type != CardType.STATUS || this.costForTurn >= -1 || (((AbstractCharBoss)m).hasRelic(MedicalKit.ID))) && (this.type != CardType.CURSE || this.costForTurn >= -1 || (((AbstractCharBoss)m).hasRelic(BlueCandle.ID))) && (this.cardPlayable(m) && this.hasEnoughEnergy());
    	} else {
    		return super.canUse(p, m);
    	}
    }
    
}
