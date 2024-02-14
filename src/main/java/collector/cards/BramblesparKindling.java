package collector.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class BramblesparKindling extends AbstractCollectorCard {
    public final static String ID = makeID(BramblesparKindling.class.getSimpleName());
    // intellij stuff skill, none, uncommon, , , , , 5, 2

    public BramblesparKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        cardsToPreview = new BurningStrike();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractCard q = new BurningStrike();
//        if (upgraded) {
//            q.upgrade();
//        }
        makeInHand(q);
    }

    public void upp() {
        selfRetain = true;
        uDesc();
    }
}