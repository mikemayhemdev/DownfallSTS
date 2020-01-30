package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnIronWave extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Iron Wave";
    private static final CardStrings cardStrings;
    
    public EnIronWave() {
        super(ID, EnIronWave.cardStrings.NAME, "red/attack/iron_wave", 1, EnIronWave.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.baseBlock = 5;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new WaitAction(0.1f));
        if (p != null && m != null) {
            this.addToBot(new VFXAction(new IronWaveEffect(m.hb.cX, m.hb.cY, p.hb.cX), 0.5f));
        }
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeBlock(2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnIronWave();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Iron Wave");
    }
}
