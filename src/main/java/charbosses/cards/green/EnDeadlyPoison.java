package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class EnDeadlyPoison extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Deadly Poison";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(DeadlyPoison.ID);
    }

    public EnDeadlyPoison() {
        super(ID, EnDeadlyPoison.cardStrings.NAME, "green/skill/deadly_poison", 1, EnDeadlyPoison.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.DEBUFF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return magicNumber * 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, m, new PoisonPower(p, m, magicNumber), magicNumber));
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
        return new EnDeadlyPoison();
    }
}
