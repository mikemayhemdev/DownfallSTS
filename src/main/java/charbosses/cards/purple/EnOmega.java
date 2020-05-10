package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.OmegaPower;

import java.util.ArrayList;

public class EnOmega extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Omega";
    private static final CardStrings cardStrings;

    public EnOmega() {
        super("Omega", cardStrings.NAME, "colorless/power/omega", 3, cardStrings.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF, AbstractMonster.Intent.ATTACK);
        this.exhaust = true;
    }

    public AbstractCard makeCopy() {
        return new EnOmega();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(10);
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new OmegaPower(m, this.magicNumber), this.magicNumber));
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Omega");
    }
}
