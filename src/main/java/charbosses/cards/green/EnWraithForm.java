package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.BossIntangiblePower;
import charbosses.powers.cardpowers.EnemyWraithFormPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.WraithFormPower;

import java.util.ArrayList;

public class EnWraithForm extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Wraith Form";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(WraithForm.ID);
    }

    public EnWraithForm() {
        super(ID, EnWraithForm.cardStrings.NAME, "green/power/wraith_form", 3, EnWraithForm.cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new BossIntangiblePower(m, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(m, m, new EnemyWraithFormPower(m, -1), -1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 100;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnWraithForm();
    }
}
