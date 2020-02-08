package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class EnFlex extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Flex";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Flex");
    }

    public EnFlex() {
        super(ID, EnFlex.cardStrings.NAME, "red/skill/flex", 0, EnFlex.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(m, m, new LoseStrengthPower(m, this.magicNumber), this.magicNumber));
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnFlex();
    }
}
