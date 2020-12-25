package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyLightning;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;

public class EnZap extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Zap";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Zap");
    }

    public EnZap() {
        super(ID, cardStrings.NAME, "blue/skill/zap", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        alwaysDisplayText = true;
    }

    public static int getFocusAmountSafe() {
        if (AbstractCharBoss.boss.hasPower(FocusPower.POWER_ID)) {
            return AbstractCharBoss.boss.getPower(FocusPower.POWER_ID).amount;
        }
        return 0;
    }

    @Override
    public String overrideIntentText() {
        return "(" + ( 3 + AbstractEnemyOrb.masterPretendFocus + getFocusAmountSafe()) + ")";
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new EnemyLightning()));
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
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
}