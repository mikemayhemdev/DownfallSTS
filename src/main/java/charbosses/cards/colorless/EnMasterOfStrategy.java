package charbosses.cards.colorless;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnMasterOfStrategy extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Master of Strategy";
    private static final CardStrings cardStrings;

    public EnMasterOfStrategy() {
        super(ID, cardStrings.NAME, "colorless/skill/master_of_strategy", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.NONE);
        this.baseMagicNumber = this.magicNumber = 3;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    public AbstractCard makeCopy() {
        return new EnMasterOfStrategy();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Master of Strategy");
    }
}
