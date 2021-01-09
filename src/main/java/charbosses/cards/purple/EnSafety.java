package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnSafety extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Safety";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Safety");
    }

    public EnSafety() {
        super(ID, cardStrings.NAME, "colorless/skill/safety", -2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 12;
        this.selfRetain = true;
        this.exhaust = true;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.upgradeBaseCost(1);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSafety();
    }
}
