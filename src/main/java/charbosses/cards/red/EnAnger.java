package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.actions.common.EnemyMakeTempCardInDiscardAction;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnAnger extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Anger";
    private static final CardStrings cardStrings;
    
    public EnAnger() {
        super(ID, EnAnger.cardStrings.NAME, "red/attack/anger", 0, EnAnger.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 6;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new VFXAction(m, new VerticalAuraEffect(Color.FIREBRICK, m.hb.cX, m.hb.cY), 0.0f));
        this.addToBot(new EnemyMakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));
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
        return new EnAnger();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Anger");
    }
}
