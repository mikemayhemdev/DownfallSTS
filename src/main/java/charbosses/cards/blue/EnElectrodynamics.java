package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyLightning;
import charbosses.powers.cardpowers.EnemyCreativeAIPower;
import charbosses.powers.cardpowers.EnemyElectroPower;
import charbosses.powers.cardpowers.EnemyStormPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Storm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ElectroPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StormPower;

import java.util.ArrayList;

public class EnElectrodynamics extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Electrodynamics";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Electrodynamics");
    }

    public EnElectrodynamics() {
            super(ID, cardStrings.NAME, "blue/power/electrodynamics", 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 2;
        this.baseMagicNumber = 2;
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
        if (AbstractCharBoss.boss.hasPower(EnemyStormPower.POWER_ID)) {
            int count=this.magicNumber+AbstractCharBoss.boss.getPower(EnemyStormPower.POWER_ID).amount;
            return "(" + ( 3 + AbstractEnemyOrb.masterPretendFocus + getFocusAmountSafe()) +"×"+count+ ")";
        }else
        return "(" + ( 3 + AbstractEnemyOrb.masterPretendFocus + getFocusAmountSafe()) +"×"+this.magicNumber+ ")";
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(EnemyElectroPower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(m, m, new EnemyElectroPower(m)));
              }
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
            this.upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new EnElectrodynamics();
    }
}