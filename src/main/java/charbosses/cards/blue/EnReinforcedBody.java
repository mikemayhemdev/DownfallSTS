package charbosses.cards.blue;

import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyEvokeWithoutRemovingOrbAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnReinforcedBody extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Reinforced Body";
    private static final CardStrings cardStrings;
    int cost;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Reinforced Body");
    }

    public EnReinforcedBody() {
        this(2);
    }

    public EnReinforcedBody(int inCost) {
        super(ID, cardStrings.NAME, "blue/skill/reinforced_body", inCost, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.baseBlock = 7;
        cost = inCost;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < cost; i++) {
            this.addToBot(new GainBlockAction(m, m, this.block));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
    }

    public AbstractCard makeCopy() {
        return new EnReinforcedBody();
    }
}
