package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class EnPerfectedStrike extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Perfected Strike";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Perfected Strike");
    }

    public EnPerfectedStrike() {
        super(ID, EnPerfectedStrike.cardStrings.NAME, "red/attack/perfected_strike", 2, EnPerfectedStrike.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 6;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
        this.tags.add(downfallMod.CHARBOSS_ATTACK);
    }

    public static int countCards() {
        int count = 6;
        /*
        for (final AbstractCard c : AbstractCharBoss.boss.hand.group) {
            if (isStrike(c)) {
                ++count;
            }
        }
        for (final AbstractCard c : AbstractCharBoss.boss.drawPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }
        for (final AbstractCard c : AbstractCharBoss.boss.discardPile.group) {
            if (isStrike(c)) {
                ++count;
            }
        }
        */
        return count;
    }

    public static boolean isStrike(final AbstractCard c) {
        return c.hasTag(CardTags.STRIKE);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        final int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    /*
    @Override
    public void genPreview() {
        final int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.genPreview();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }
    */

    @Override
    public AbstractCard makeCopy() {
        return new EnPerfectedStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = EnPerfectedStrike.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
