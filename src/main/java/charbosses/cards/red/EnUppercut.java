package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class EnUppercut extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Uppercut";
    private static final CardStrings cardStrings;

    public EnUppercut() {
        super(ID, cardStrings.NAME, "red/attack/uppercut", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 13;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        artifactConsumedIfPlayed = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber + 1, false), this.magicNumber + 1));
        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, this.magicNumber + 1, false), this.magicNumber + 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Uppercut");
    }
    @Override
    public AbstractCard makeCopy() {
        return new EnUppercut();
    }
}
