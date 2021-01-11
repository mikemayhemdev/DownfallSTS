package charbosses.cards.green;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnBladeDance extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Blade Dance";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blade Dance");
    }

    public EnBladeDance() {
        super(ID, EnBladeDance.cardStrings.NAME, "green/skill/blade_dance", 1, EnBladeDance.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.NONE, AbstractMonster.Intent.ATTACK);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new EnShiv();
        this.magicValue = 4;
        this.baseDamage = 4;
        isMultiDamage = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnShiv(), this.magicNumber));
    }


    @Override
    public int customIntentModifiedDamage() {
        int tmp = 0;

        if (owner.hasPower(EnemyAccuracyPower.POWER_ID)){
            tmp = owner.getPower(EnemyAccuracyPower.POWER_ID).amount;
        }
        return tmp;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnBladeDance();
    }
}
