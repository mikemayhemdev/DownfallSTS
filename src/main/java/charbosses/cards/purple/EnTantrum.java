package charbosses.cards.purple;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.Watcher.NewAge.ArchetypeAct2StancesNewAge;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyMentalFortressPower;
import charbosses.stances.AbstractEnemyStance;
import charbosses.stances.EnRealWrathStance;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnTantrum extends AbstractStanceChangeCard {
    public static final String ID = "downfall_Charboss:Tantrum";
    private static final CardStrings cardStrings;
    boolean realWrath = false;

    public EnTantrum() {
        super(ID, cardStrings.NAME, "purple/attack/tantrum", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 3;
        this.baseMagicNumber = 3;
        this.magicNumber = 3;
        this.isMultiDamage = true;
        intentMultiAmt = this.magicNumber;
        this.shuffleBackIntoDrawPile = true;
    }

    public EnTantrum(boolean realWrath) {
        this();
        this.realWrath = realWrath;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        this.addToBot(new EnemyChangeStanceAction("Real Wrath"));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return super.getPriority(hand) + 10;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnTantrum();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Tantrum");
    }

    @Override
    public AbstractEnemyStance changeStanceForIntentCalc(AbstractEnemyStance previousStance) {
        return new EnRealWrathStance();
    }
}
