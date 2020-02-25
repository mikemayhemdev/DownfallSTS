package charbosses.cards.red;

import charbosses.actions.utility.EnemyExhaustAllEtherealAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnGhostlyArmor extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Ghostly Armor";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghostly Armor");
    }

    public EnGhostlyArmor() {
        super(ID, EnGhostlyArmor.cardStrings.NAME, "red/skill/ghostly_armor", 1, EnGhostlyArmor.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.isEthereal = true;
        this.baseBlock = 10;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        this.addToTop(new EnemyExhaustAllEtherealAction());
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
        return new EnGhostlyArmor();
    }
}
