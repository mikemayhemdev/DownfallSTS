package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnDefendPurple extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Defend_P";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_P");
    }

    public EnDefendPurple() {
        super(ID, cardStrings.NAME, "purple/skill/defend", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDefendPurple();
    }
}
