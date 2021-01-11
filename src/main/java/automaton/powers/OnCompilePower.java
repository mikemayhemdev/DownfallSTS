package automaton.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnCompilePower {
    void receiveCompile(AbstractCard function, boolean forGameplay);
}
