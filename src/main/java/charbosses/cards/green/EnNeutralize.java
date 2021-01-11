package charbosses.cards.green;

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
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnNeutralize extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Neutralize";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Neutralize");
    }

    public EnNeutralize() {
        super(ID, EnNeutralize.cardStrings.NAME, "green/attack/neutralize", 0, EnNeutralize.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.BASIC, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.magicValue = 3;
        artifactConsumedIfPlayed = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (p.hasPower(WeakPower.POWER_ID))
            this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber, true), this.magicNumber));
        else
            this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber + 1, true), this.magicNumber + 1));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnNeutralize();
    }
}
