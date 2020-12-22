package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import java.util.ArrayList;

public class EnSwivel extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Swivel";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Swivel");
    }

    public EnSwivel() {
        super(ID, cardStrings.NAME, "purple/skill/swivel", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 8;
        this.magicNumber = this.baseMagicNumber;
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
      //  this.addToBot(new ApplyPowerAction(m, m, new FreeAttackPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSwivel();
    }
}
