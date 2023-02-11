package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.ShadowCloak;
import hermit.characters.hermit;
import hermit.powers.ShadowCloakPower;

public class EnShadowCloak extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:ShadowClock";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ShadowCloak.ID);

    public EnShadowCloak() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/shadow_cloak.png", 1, cardStrings.DESCRIPTION, CardType.POWER, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new ShadowCloakPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnShadowCloak();
    }
}
