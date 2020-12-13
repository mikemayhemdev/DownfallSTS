package charbosses.cards.green;

import charbosses.actions.common.EnemyMakeTempCardInHandAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import charbosses.powers.general.EnemyPoisonPower;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class EnCloakAndDagger extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Cloak And Dagger";
    private static final CardStrings cardStrings;


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Cloak And Dagger");
    }

    public EnCloakAndDagger() {
        super(ID, EnCloakAndDagger.cardStrings.NAME, "green/skill/cloak_and_dagger", 1, EnCloakAndDagger.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.ATTACK_DEFEND);
        this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 1;
        this.cardsToPreview = new EnShiv();
        this.magicValue = 4;
        this.baseDamage = 4;
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
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyMakeTempCardInHandAction(new EnShiv(), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            isMultiDamage = true;
            this.upgradeMagicNumber(1);
            this.rawDescription = EnCloakAndDagger.cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (upgraded) {
            return autoPriority() * 2;
        } else {
            return autoPriority();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCloakAndDagger();
    }
}
