package charbosses.cards.colorless;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnShiv extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Shiv";
    public static final int ATTACK_DMG = 4;
    public static final int UPG_ATTACK_DMG = 2;
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shiv");
    }

    public EnShiv() {
        super(ID, EnShiv.cardStrings.NAME, "colorless/attack/shiv", 0, EnShiv.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        if (AbstractCharBoss.boss != null && AbstractCharBoss.boss.hasPower(EnemyAccuracyPower.POWER_ID)) {
            this.baseDamage = 4 + AbstractCharBoss.boss.getPower(EnemyAccuracyPower.POWER_ID).amount;
        } else {
            this.baseDamage = 4;
        }
        this.exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnShiv();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }
}
