package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.cards.Virtue;
import hermit.characters.hermit;

public class EnVirtue extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Virtue";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Virtue.ID);

    public EnVirtue() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/virtue.png", 0, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);

        this.selfRetain = true;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (AbstractPower pow : m.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                addToBot(new ReducePowerAction(m, m, pow.ID, magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnVirtue();
    }
}
