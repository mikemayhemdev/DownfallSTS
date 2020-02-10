package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;

public class EnZap extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Zap";
    private static final CardStrings cardStrings;

    public EnZap() {
        super(ID, cardStrings.NAME, "blue/skill/zap", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.BASIC, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new Lightning()));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy() {
        return new EnZap();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Zap");
    }
}