package charbosses.cards.purple;

import charbosses.actions.unique.EnemyHaltAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnHalt extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Halt";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Halt");
    }

    public EnHalt() {
        super(ID, cardStrings.NAME, "purple/skill/halt", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.block = this.baseBlock = 3;
        this.magicNumber = this.baseMagicNumber = 9;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        applyPowers();
        this.addToBot(new EnemyHaltAction(m, this.block, this.magicNumber));
    }

    @Override
    public void applyPowers() {
        this.baseBlock += 6 + this.timesUpgraded * 4;
        this.baseMagicNumber = this.baseBlock;
        super.applyPowers();
        this.magicNumber = this.block;
        this.isMagicNumberModified = this.isBlockModified;
        this.baseBlock -= 6 + this.timesUpgraded * 4;
        super.applyPowers();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(1);
            this.baseMagicNumber = this.baseBlock + 6 + this.timesUpgraded * 4;
            this.upgradedMagicNumber = this.upgradedBlock;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnHalt();
    }
}
