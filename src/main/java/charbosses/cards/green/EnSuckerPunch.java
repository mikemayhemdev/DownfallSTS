package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyEnergizedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.SuckerPunch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnSuckerPunch extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Sucker Punch";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(SuckerPunch.ID);
    }

    public EnSuckerPunch() {
        super(ID, EnSuckerPunch.cardStrings.NAME, "green/attack/sucker_punch", 1, EnSuckerPunch.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 8;
        this.baseMagicNumber = magicNumber = 1;
        artifactConsumedIfPlayed = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, magicNumber + 1, true), magicNumber + 1));
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnSuckerPunch();
    }
}
