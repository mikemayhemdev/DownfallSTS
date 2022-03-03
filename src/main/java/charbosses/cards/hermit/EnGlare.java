package charbosses.cards.hermit;

import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.Dive;
import hermit.cards.Glare;
import hermit.characters.hermit;

public class EnGlare extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Glare";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Glare.ID);

    public EnGlare() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/glare.png", 0, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, 1, true), 1));
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 1, true), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnGlare();
    }
}
