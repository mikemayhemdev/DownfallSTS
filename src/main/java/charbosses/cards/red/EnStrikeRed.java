package charbosses.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnStrikeRed extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Strike_R";
    private static final CardStrings cardStrings;
    
    public EnStrikeRed() {
        super(ID, EnStrikeRed.cardStrings.NAME, "red/attack/strike", 1, EnStrikeRed.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.tags.add(CardTags.STRIKE);
        this.tags.add(CardTags.STARTER_STRIKE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
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
        return new EnStrikeRed();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_R");
    }
}
