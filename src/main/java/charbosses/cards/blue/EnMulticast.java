package charbosses.cards.blue;

import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyEvokeWithoutRemovingOrbAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnMulticast extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Multi-Cast";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Multi-Cast");
    }

    public EnMulticast() {
        super(ID, cardStrings.NAME, "blue/skill/multicast", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 1; i < this.cost-1; i++) {
            this.addToBot(new EnemyAnimateOrbAction(1));
            this.addToBot(new EnemyEvokeWithoutRemovingOrbAction(1));
        }
        this.addToBot(new EnemyAnimateOrbAction(1));
        this.addToBot(new EnemyEvokeOrbAction(1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new EnMulticast();
    }
}
