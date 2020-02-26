package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;

import java.util.ArrayList;

public class EnBarricade extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Barricade";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Barricade");
    }

    public EnBarricade() {
        super(ID, EnBarricade.cardStrings.NAME, "red/power/barricade", 3, EnBarricade.cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = this.magicNumber = 1;
        this.limit = 2;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        boolean powerExists = false;
        for (final AbstractPower pow : m.powers) {
            if (pow.ID.equals("Barricade")) {
                powerExists = true;
                break;
            }
        }
        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(m, m, new BarricadePower(m)));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBarricade();
    }
}
