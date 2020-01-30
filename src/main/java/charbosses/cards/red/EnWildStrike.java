package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.actions.common.EnemyMakeTempCardInDrawPileAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.status.EnWound;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnWildStrike extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Wild Strike";
    private static final CardStrings cardStrings;
    
    public EnWildStrike() {
        super(ID, EnWildStrike.cardStrings.NAME, "red/attack/wild_strike", 1, EnWildStrike.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.tags.add(CardTags.STRIKE);
        this.cardsToPreview = new Wound();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new EnemyMakeTempCardInDrawPileAction(new EnWound(), 1, true, true));
    }
    
    @Override
    public int getValue() {
    	return super.getValue() + AbstractBossCard.statusValue();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EnWildStrike();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wild Strike");
    }
}