package charbosses.cards.green;

import charbosses.actions.common.EnemyDiscardAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnSurvivor extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Survivor";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Survivor");
    }

    public EnSurvivor() {
        super(ID, EnSurvivor.cardStrings.NAME, "green/skill/survivor", 1, EnSurvivor.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 8;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyDiscardAction(m, m, 1, false));
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
        return new EnSurvivor();
    }
}
