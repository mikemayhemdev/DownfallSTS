package charbosses.cards.blue;

import charbosses.actions.orb.EnemyIncreaseMaxOrbAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import java.util.ArrayList;

public class EnBuffer extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Buffer";
    private static final CardStrings cardStrings;

    public EnBuffer() {
        super(ID, cardStrings.NAME, "blue/power/buffer", 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
       // alwaysDisplayText = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new ApplyPowerAction(m, m, new BufferPower(m, this.magicNumber), this.magicNumber));

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
        return new EnBuffer();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Buffer");
    }
}
