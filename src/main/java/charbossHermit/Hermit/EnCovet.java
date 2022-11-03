package charbossHermit.Hermit;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.cards.Covet;

public class EnCovet extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Covet";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Covet.ID);

    public EnCovet() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/covet.png", 0, cardStrings.DESCRIPTION, CardType.SKILL, downfallMod.Enums.COLOR_YELLOW, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnCovet();
    }
}
