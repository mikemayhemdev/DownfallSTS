package timeEater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeEater.potions.StoredCardPotion;

public class Defer extends AbstractTimeCard {

    public final static String ID = makeID("Defer");

    // intellij stuff skill, self, rare

    public Defer() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, "Choose.", (cards) -> {
            AbstractCard c = cards.get(0);
            addToTop(new ObtainPotionAction(new StoredCardPotion(c)));
            addToTop(new ExhaustSpecificCardAction(c, p.hand));
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}