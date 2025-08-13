package charbosses.cards.hermit;

import charbosses.powers.cardpowers.EnemyShadowCloakPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Reprieve;
import hermit.cards.ShadowCloak;
import hermit.characters.hermit;

public class EnReprieve extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Reprieve";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Reprieve.ID);

    public EnReprieve() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/reprieve.png", 2, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {this.addToBot(new HealAction(m, m, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnReprieve();
    }
}
