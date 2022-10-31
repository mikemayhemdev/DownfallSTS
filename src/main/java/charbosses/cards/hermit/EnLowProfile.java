package charbosses.cards.hermit;

import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hermit.cards.Dive;
import hermit.cards.LowProfile;
import hermit.characters.hermit;

public class EnLowProfile extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:LowProfile";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(LowProfile.ID);

    public EnLowProfile() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/low_profile.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 7;
        baseMagicNumber = magicNumber = 4;
    }

    private int countDebuffs()
    {
        int debuffs = 0;
        for (AbstractPower pow: this.owner.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF)
                debuffs++;
        }
        return debuffs;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, m, block));
    }

    public void applyPowers() {
        int realBaseBlock = this.baseBlock;

        this.baseBlock += this.magicNumber * countDebuffs();
        super.applyPowers();

        this.baseBlock = realBaseBlock;
        this.isBlockModified = this.block != this.baseBlock;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnLowProfile();
    }
}
