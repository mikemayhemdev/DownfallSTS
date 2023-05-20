package charbosses.cards.other;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.expansionContentMod;

import static expansioncontent.cards.AbstractExpansionCard.makeID;

public class Antidote extends AbstractDownfallCard {
    public static final String ID = makeID("Antidote");
    public static final String IMG_PATH = expansionContentMod.makeCardPath("Antidote.png");
    private static final CardStrings cardStrings;

    public Antidote() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RemoveSpecificPowerAction(p, p, PoisonPower.POWER_ID));
    }

    public void upp () {
        upgradeBaseCost(0);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}



