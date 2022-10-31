package charbosses.cards.colorless;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;

import java.util.ArrayList;

public class EnJAX extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Jax";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("J.A.X.");
    }

    public EnJAX() {
        super(ID, EnJAX.cardStrings.NAME, "colorless/skill/jax", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        strengthGeneratedIfPlayed = magicNumber;
        this.tags.add(CardTags.HEALING);
        this.tags.add(downfallMod.CHARBOSS_SETUP);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        this.addToBot(new LoseHPAction(m, m, 3));
        this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            strengthGeneratedIfPlayed++;
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return  Integer.MAX_VALUE;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnJAX();
    }
}
