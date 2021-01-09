package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnGeneticAlgorithm extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Genetic Algorithm";
    private static final CardStrings cardStrings;

    public EnGeneticAlgorithm() {
        super("Genetic Algorithm", cardStrings.NAME, "blue/skill/genetic_algorithm", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 14;
        this.exhaust = true;
    }

    public EnGeneticAlgorithm(int preBlock){
        this();
        this.baseBlock = preBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
      //  this.addToBot(new IncreaseMiscBuglessAction(this.uuid, this.misc, this.magicNumber));
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeMagicNumber(1);
            this.upgradeName();
        }

    }

    public AbstractCard makeCopy() {
        return new GeneticAlgorithm();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Genetic Algorithm");
    }
}
