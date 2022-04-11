package charbosses.cards.blue;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyEvokeWithoutRemovingOrbAction;
import charbosses.cards.AbstractBossCard;
import charbosses.ui.EnemyEnergyPanel;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
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
        super(ID, cardStrings.NAME, "blue/skill/reinforced_body", inCost, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.NONE, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 7;
        cost = inCost;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        int realCost = this.owner.energyPanel.getCurrentEnergy();
        for (int i = 0; i < realCost; i++) {
            this.addToBot(new GainBlockAction(m, m, this.block));
        }
        // TODO: ew
        EnemyEnergyPanel.useEnergy(realCost - cost);
        this.owner.hand.glowCheck();
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
