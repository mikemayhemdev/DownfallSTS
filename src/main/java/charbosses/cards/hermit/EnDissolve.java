package charbosses.cards.hermit;

import charbosses.powers.cardpowers.EnemyShadowCloakPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import hermit.cards.Dissolve;
import hermit.cards.ShadowCloak;
import hermit.characters.hermit;

public class EnDissolve extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Dissolve";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Dissolve.ID);

    public EnDissolve() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/dissolve.png", 2, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 18;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(m, m, new BlurPower(m, baseMagicNumber), baseMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(7);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDissolve();
    }
}
