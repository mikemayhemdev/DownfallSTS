package charbosses.cards.colorless;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyStormPower;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.SadisticPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;
import java.util.Iterator;

public class EnTrip extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Trip";
    private static final CardStrings cardStrings;


    public EnTrip() {
        super(ID, cardStrings.NAME, "colorless/skill/trip", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.DEBUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        artifactConsumedIfPlayed = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, this.magicNumber, true), this.magicNumber));


    }

    public AbstractCard makeCopy() {
        return new EnTrip();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public String overrideIntentText() {
        if (AbstractCharBoss.boss.hasPower(SadisticPower.POWER_ID)) {
            int count=this.magicNumber+AbstractCharBoss.boss.getPower(SadisticPower.POWER_ID).amount;
            return "(" +count+ ")";
        }
        return super.overrideIntentText();
    }


    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Trip");
    }
}
