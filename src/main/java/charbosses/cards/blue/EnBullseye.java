package charbosses.cards.blue;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class EnBullseye extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Lockon";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Lockon");
    }

    public EnBullseye() {
        super("Lockon", cardStrings.NAME, "blue/attack/lock_on", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
                    if (o instanceof AbstractEnemyOrb) {
                        ((AbstractEnemyOrb) o).pretendLockOn = false;
                        o.applyFocus();
                    }
                }
            }
        });
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.addToBot(new ApplyPowerAction(p, m, new LockOnPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBullseye();
    }
}
