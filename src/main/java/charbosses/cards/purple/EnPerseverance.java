package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Perseverance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnPerseverance extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Perseverance";
    private static final CardStrings cardStrings;

    public EnPerseverance() {
        super(ID, cardStrings.NAME, "purple/skill/perseverance", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 5;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
    }

    public EnPerseverance(int preBlock){
        this();
        this.baseBlock += preBlock;
        this.block = this.baseBlock;
    }

    public void onRetained() {
        this.upgradeBlock(this.magicNumber);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnPerseverance();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Perseverance");
    }
}
