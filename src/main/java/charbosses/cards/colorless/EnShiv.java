package charbosses.cards.colorless;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnShiv extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Shiv";
    private static final CardStrings cardStrings;
    public static final int ATTACK_DMG = 4;
    public static final int UPG_ATTACK_DMG = 2;
    
    public EnShiv() {
        super(ID, EnShiv.cardStrings.NAME, "colorless/attack/shiv", 0, EnShiv.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        if (AbstractCharBoss.boss != null && AbstractCharBoss.boss.hasPower(EnemyAccuracyPower.POWER_ID)) {
            this.baseDamage = 4 + AbstractCharBoss.boss.getPower(EnemyAccuracyPower.POWER_ID).amount;
        }
        else {
            this.baseDamage = 4;
        }
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnShiv();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shiv");
    }
}
