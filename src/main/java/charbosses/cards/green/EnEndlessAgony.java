package charbosses.cards.green;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.EndlessAgony;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnEndlessAgony extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Endless Agony";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(EndlessAgony.ID);
    }

    public EnEndlessAgony() {
        super(ID, EnEndlessAgony.cardStrings.NAME, "green/attack/endless_agony", 0, EnEndlessAgony.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 4;
        exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new EnemyMakeTempCardInHandAction(this.makeStatEquivalentCopy()));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnEndlessAgony();
    }
}
