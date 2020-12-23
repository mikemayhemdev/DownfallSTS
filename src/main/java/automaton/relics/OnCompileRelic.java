package automaton.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnCompileRelic {
    void receiveCompile(AbstractCard function, boolean forGameplay);
}
