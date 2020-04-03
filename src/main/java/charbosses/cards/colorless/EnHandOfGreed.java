package charbosses.cards.colorless;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class EnHandOfGreed extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:HandOfGreed";
    private static final CardStrings cardStrings;

    public EnHandOfGreed() {
        super(ID, cardStrings.NAME, "colorless/attack/hand_of_greed", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 20;
        this.baseMagicNumber = 20;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.upgradeMagicNumber(5);
        }

    }

    public AbstractCard makeCopy() {
        return new EnHandOfGreed();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("HandOfGreed");
    }
}