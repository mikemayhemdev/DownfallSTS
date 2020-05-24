package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnCripplingCloud extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Crippling Cloud";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(EnCripplingCloud.ID);
    }

    public EnCripplingCloud() {
        super(ID, EnCripplingCloud.cardStrings.NAME, "green/skill/crippling_cloud", 2, EnCripplingCloud.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        exhaust = true;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return magicNumber * 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new PoisonPower(p, m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, 2, false), 2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCripplingCloud();
    }
}
