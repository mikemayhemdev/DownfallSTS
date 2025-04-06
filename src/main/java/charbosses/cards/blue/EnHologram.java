package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Leap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnHologram extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Hologram";
    private static final CardStrings cardStrings;

    public EnHologram() {
        super(ID, cardStrings.NAME, "blue/skill/hologram", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 3;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust=false;
            this.upgradeBlock(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new EnHologram();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hologram");
    }
}
