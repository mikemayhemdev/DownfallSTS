package charbosses.cards.crowbot;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyDamageMultiplierPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnBoom extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Boom";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnBoom() {
        super(ID, EnBoom.cardStrings.NAME, "crowbot/boom", 1, EnBoom.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.BASIC, CardTarget.ENEMY, AbstractMonster.Intent.BUFF, true);
        this.magicNumber = this.baseMagicNumber = 2;

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new EnemyDamageMultiplierPower(m, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBoom();
    }
}
