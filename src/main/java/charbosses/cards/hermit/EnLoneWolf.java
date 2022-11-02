package charbosses.cards.hermit;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.cards.LoneWolf;

public class EnLoneWolf extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:LoneWolf";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(LoneWolf.ID);

    public EnLoneWolf() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/lone_wolf.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, downfallMod.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.NONE);
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
