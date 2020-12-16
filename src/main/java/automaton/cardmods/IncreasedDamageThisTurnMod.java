package automaton.cardmods;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampMod;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
