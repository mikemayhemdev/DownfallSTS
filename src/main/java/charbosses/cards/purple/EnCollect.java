package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import charbosses.powers.cardpowers.EnemyCollectPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnCollect extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Collect";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Collect");
    }

    public EnCollect() {
        super(ID, cardStrings.NAME, "purple/skill/collect", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.cardsToPreview = new Miracle();
        this.cardsToPreview.upgrade();
        this.exhaust = true;
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, m, new EnemyCollectPower(m, this.magicNumber), this.magicNumber));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnCollect();
    }
}
