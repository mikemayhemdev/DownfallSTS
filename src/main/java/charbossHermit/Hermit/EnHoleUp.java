package charbossHermit.Hermit;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.downfallMod;
import hermit.cards.HoleUp;

public class EnHoleUp extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:HoleUp";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(HoleUp.ID);

    public EnHoleUp() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/hole_up.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, downfallMod.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 12;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, block));
        addToBot(new ApplyPowerAction(m, m, new WeakPower(m, magicNumber, true), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnHoleUp();
    }
}