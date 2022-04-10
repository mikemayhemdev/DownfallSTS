package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyFearNoEvilPower;
import charbosses.powers.cardpowers.EnemySpotWeaknessPower;
import charbosses.stances.AbstractEnemyStance;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class EnFearNoEvil extends AbstractStanceChangeCard {
    public static final String ID = "downfall_Charboss:FearNoEvil";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FearNoEvil");
    }

    public EnFearNoEvil() {
        super(ID, EnFearNoEvil.cardStrings.NAME, "purple/attack/fear_no_evil", 1, EnFearNoEvil.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 8;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        System.out.println("Playing fear no evil: " + m.hasPower(EnemyFearNoEvilPower.POWER_ID));
        if (m.hasPower(EnemyFearNoEvilPower.POWER_ID)) {
            EnemyFearNoEvilPower power = (EnemyFearNoEvilPower) m.getPower(EnemyFearNoEvilPower.POWER_ID);
            if (power.isActive) {
                this.flash();
                this.addToBot(new EnemyChangeStanceAction("Calm"));
            }
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, power));
            System.out.println("Removed " +EnemyFearNoEvilPower.POWER_ID + ": " + m.hasPower(EnemyFearNoEvilPower.POWER_ID));
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return super.getPriority(hand) + 10;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnFearNoEvil();
    }

    @Override
    public AbstractEnemyStance changeStanceForIntentCalc(AbstractEnemyStance previousStance) {
        if (this.owner.hasPower(EnemyFearNoEvilPower.POWER_ID)) {
            EnemyFearNoEvilPower power = (EnemyFearNoEvilPower) this.owner.getPower(EnemyFearNoEvilPower.POWER_ID);
            if (power.isActive) {
                return new EnCalmStance();
            }
        }
        return previousStance;
    }
}
