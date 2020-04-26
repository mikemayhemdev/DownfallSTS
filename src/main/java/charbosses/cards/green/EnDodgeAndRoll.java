package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class EnDodgeAndRoll extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Dodge and Roll";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dodge and Roll");
    }

    public EnDodgeAndRoll() {
        super(ID, EnDodgeAndRoll.cardStrings.NAME, "green/skill/dodge_and_roll", 1, EnDodgeAndRoll.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 4;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(m, m, new NextTurnBlockPower(m, this.block), this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    @Override
    public int getValue() {
        return super.getValue() * 2;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDodgeAndRoll();
    }
}
