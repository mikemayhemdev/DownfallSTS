package charbosses.cards.blue;

import charbosses.actions.orb.EnemyIncreaseMaxOrbAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnCapacitor extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Capacitor";
    private static final CardStrings cardStrings;

    public EnCapacitor() {
        super(ID, cardStrings.NAME, "blue/power/capacitor", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        alwaysDisplayText = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new EnemyIncreaseMaxOrbAction(this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 20;
    }

    public AbstractCard makeCopy() {
        return new EnCapacitor();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Capacitor");
    }
}
