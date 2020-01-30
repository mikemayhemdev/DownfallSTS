package charbosses.cards.colorless;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnBite extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Bite";
    private static final CardStrings cardStrings;
    
    public EnBite() {
        super(ID, EnBite.cardStrings.NAME, "colorless/attack/bite", 1, EnBite.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.HEALING);
        this.magicValue = 1;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (m != null) {
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(new BiteEffect(p.hb.cX, p.hb.cY - 40.0f * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.1f));
            }
            else {
                this.addToBot(new VFXAction(new BiteEffect(p.hb.cX, p.hb.cY - 40.0f * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.3f));
            }
        }
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new HealAction(m, m, this.magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnBite();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bite");
    }
}
