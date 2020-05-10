package charbosses.cards.blue;

import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyEvokeWithoutRemovingOrbAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Dualcast;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnDualcast extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Dualcast";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dualcast");
    }

    public EnDualcast() {
        super(ID, cardStrings.NAME, "blue/skill/dualcast", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.BASIC, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyAnimateOrbAction(1));
        this.addToBot(new EnemyEvokeWithoutRemovingOrbAction(1));
        this.addToBot(new EnemyAnimateOrbAction(1));
        this.addToBot(new EnemyEvokeOrbAction(1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy() {
        return new EnDualcast();
    }
}
