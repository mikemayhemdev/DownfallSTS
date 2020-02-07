package charbosses.cards.green;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnDaggerSpray extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Dagger Spray";
    private static final CardStrings cardStrings;
    
    public EnDaggerSpray() {
        super(ID, EnDaggerSpray.cardStrings.NAME, "green/attack/dagger_spray", 1, EnDaggerSpray.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 4;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(new DaggerSprayEffect(true), 0.0f));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new VFXAction(new DaggerSprayEffect(true), 0.0f));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }
    
    @Override
    public int getValue() {
    	return super.getValue() * 2;
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnDaggerSpray();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dagger Spray");
    }
}
