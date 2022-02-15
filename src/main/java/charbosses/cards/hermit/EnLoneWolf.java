package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hermit.cards.GhostlyPresence;
import hermit.cards.LoneWolf;
import hermit.characters.hermit;

public class EnLoneWolf extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:LoneWolf";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(LoneWolf.ID);

    public EnLoneWolf() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/lone_wolf.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.NONE);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnLoneWolf();
    }
}
