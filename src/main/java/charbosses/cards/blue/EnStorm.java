package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyStormPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.blue.Storm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StormPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class EnStorm extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Storm";
    private static final CardStrings cardStrings;

    public EnStorm() {
        super(ID, cardStrings.NAME, "blue/power/storm", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractBossCard.fakeStormPower = false;
            }
        });
        this.addToBot(new ApplyPowerAction(m, m, new EnemyStormPower(m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new EnStorm();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Storm");
    }
}
