package twins.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class IncreasedDamageThisTurnMod extends AbstractCardModifier {

    private int amount;

    public IncreasedDamageThisTurnMod(int a) {
        this.amount = a;
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.baseDamage += amount;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.baseDamage -= amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new IncreasedDamageThisTurnMod(amount);
    }
}
