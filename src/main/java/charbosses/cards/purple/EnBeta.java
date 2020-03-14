package charbosses.cards.purple;

import charbosses.actions.common.EnemyMakeTempCardInDrawPileAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnBeta extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Beta";
    private static final CardStrings cardStrings;
    private boolean usedOnce;

    public EnBeta() {
        super(ID, cardStrings.NAME, "colorless/skill/beta", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE, AbstractMonster.Intent.MAGIC);
        this.exhaust = true;
    }

    public AbstractCard makeCopy() {
        return new EnBeta();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (usedOnce){
            this.addToBot(new EnemyMakeTempCardInDrawPileAction(new EnOmega(), 1, true, true));
        } else {
            this.usedOnce = true;
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Alpha");
    }
}
