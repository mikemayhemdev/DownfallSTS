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
import hermit.cards.Dive;
import hermit.cards.Manifest;
import hermit.characters.hermit;

public class EnManifest extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Manifest";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Manifest.ID);

    public EnManifest() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/manifest.png", 2, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 13;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, block));
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
        return new EnManifest();
    }
}
