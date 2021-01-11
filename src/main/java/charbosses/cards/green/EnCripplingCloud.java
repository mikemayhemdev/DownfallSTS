package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnCripplingCloud extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Crippling Poison";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(CripplingPoison.ID);
    }

    public EnCripplingCloud() {
        super(ID, EnCripplingCloud.cardStrings.NAME, "green/skill/crippling_poison", 2, EnCripplingCloud.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        exhaust = true;
        artifactConsumedIfPlayed = 2;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return magicNumber * 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new EnemyPoisonPower(p, m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, 3, false), 3));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCripplingCloud();
    }
}
