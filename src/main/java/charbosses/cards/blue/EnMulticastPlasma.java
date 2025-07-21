package charbosses.cards.blue;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyEvokeWithoutRemovingOrbAction;
import charbosses.actions.orb.EnemyRemoveOrbAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnMulticastPlasma extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Multi-Cast";
    private static final CardStrings cardStrings;
    private int cost;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Multi-Cast");
    }

    public EnMulticastPlasma() {
        this(2);
    }

    public EnMulticastPlasma(int inCost) {
        super(ID, cardStrings.NAME, "blue/skill/multicast", inCost, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        cost = inCost;
        this.showEvokeValue = true;
        this.energyGeneratedIfPlayed = inCost*2;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
//        if (m instanceof AbstractCharBoss) {
//            addToBot(new EnemyGainEnergyAction(((AbstractCharBoss) m).energyPanel.getCurrentEnergy()));
//        }
        if (m instanceof AbstractCharBoss) {
            addToBot(new EnemyGainEnergyAction(((this.cost-2))));
        }
        this.addToBot(new EnemyAnimateOrbAction(1));
        this.addToBot(new EnemyRemoveOrbAction(1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new EnMulticastPlasma();
    }
}
