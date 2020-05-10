package charbosses.cards.colorless;

import charbosses.actions.unique.EnemyApotheosisAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EnBandage extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bandage Up";
    private static final CardStrings cardStrings;

    public EnBandage() {
        super(ID, cardStrings.NAME, "colorless/skill/bandage_up", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new HealAction(m, m, this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 8;
    }

    public AbstractCard makeCopy() {
        return new EnBandage();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bandage Up");
    }
}