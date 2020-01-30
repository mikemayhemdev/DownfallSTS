package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class EnFlameBarrier extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Flame Barrier";
    private static final CardStrings cardStrings;
    
    public EnFlameBarrier() {
        super("Flame Barrier", EnFlameBarrier.cardStrings.NAME, "red/skill/flame_barrier", 2, EnFlameBarrier.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 12;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 2;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(m, new FlameBarrierEffect(m.hb.cX, m.hb.cY), 0.1f));
        }
        else {
            this.addToBot(new VFXAction(m, new FlameBarrierEffect(m.hb.cX, m.hb.cY), 0.5f));
        }
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(m, m, new FlameBarrierPower(m, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.upgradeMagicNumber(2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnFlameBarrier();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Flame Barrier");
    }
}
