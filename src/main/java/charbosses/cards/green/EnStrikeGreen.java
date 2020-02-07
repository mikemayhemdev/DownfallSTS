package charbosses.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class EnStrikeGreen extends AbstractBossCard
{
    public static final String ID = "EvilWithin_Charboss:Strike_G";
    private static final CardStrings cardStrings;
    
    public EnStrikeGreen() {
        super(ID, EnStrikeGreen.cardStrings.NAME, "green/attack/strike", 1, EnStrikeGreen.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.tags.add(CardTags.STRIKE);
        this.tags.add(CardTags.STARTER_STRIKE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
        return new EnStrikeGreen();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_G");
    }
}
