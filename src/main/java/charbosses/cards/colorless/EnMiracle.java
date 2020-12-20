package charbosses.cards.colorless;

import charbosses.actions.common.EnemyGainEnergyAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.ArrayList;

public class EnMiracle extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Miracle";
    private static final CardStrings cardStrings;


    public EnMiracle() {
        super(ID, cardStrings.NAME, "colorless/skill/miracle", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.exhaust = true;
        this.selfRetain = true;
        this.energyGeneratedIfPlayed = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EnemyGainEnergyAction(1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 20;
    }

    public AbstractCard makeCopy() {
        return new EnMiracle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Miracle");
    }
}