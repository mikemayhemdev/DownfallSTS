package charbosses.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnCleave extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Cleave";
    private static final CardStrings cardStrings;
    
    public EnCleave() {
        super(ID, EnCleave.cardStrings.NAME, "red/attack/cleave", 1, EnCleave.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 8;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(m, new CleaveEffect(), 0.1f));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage), AbstractGameAction.AttackEffect.NONE));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnCleave();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Cleave");
    }
}
