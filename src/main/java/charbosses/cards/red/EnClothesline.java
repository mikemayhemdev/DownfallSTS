package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Clothesline;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnClothesline extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Clothesline";
    private static final CardStrings cardStrings;

    public EnClothesline() {
        super(ID, cardStrings.NAME, "red/attack/clothesline", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 12;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        artifactConsumedIfPlayed = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber + 1, false), this.magicNumber + 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new EnClothesline();
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 5;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Clothesline");
    }
}
