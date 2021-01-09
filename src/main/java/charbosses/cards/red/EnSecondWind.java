package charbosses.cards.red;

import charbosses.actions.unique.EnemyBlockPerNonAttackAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.BlockPerNonAttackAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnSecondWind extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Second Wind";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Second Wind");
    }

    public EnSecondWind() {
        super(ID, cardStrings.NAME, "red/skill/second_wind", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 5;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyBlockPerNonAttackAction(this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSecondWind();
    }
}
