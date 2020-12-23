package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyBerserkPower;
import charbosses.powers.cardpowers.EnemyMetallicizePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import downfall.downfallMod;

import java.util.ArrayList;

public class EnBerserk extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Berserk";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Berserk");
    }

    public EnBerserk() {
        super(ID, cardStrings.NAME, "red/power/berserk", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.RED, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(downfallMod.CHARBOSS_SETUP);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyBerserkPower(m), 1));
        //Make apply vulnerable to itself?
        this.addToBot(new ApplyPowerAction(m, m, new VulnerablePower(p, this.magicNumber, true), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
        }
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 50;
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBerserk();
    }
}
