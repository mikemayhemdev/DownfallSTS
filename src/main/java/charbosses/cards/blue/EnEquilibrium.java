package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyFrost;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnEquilibrium extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Equilibrium";
    private static final CardStrings cardStrings;

    public EnEquilibrium() {
        super(ID, cardStrings.NAME, "blue/skill/equilibrium", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 13;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 10;
    }

    public AbstractCard makeCopy() {
        return new EnEquilibrium();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Undo");
    }
}
