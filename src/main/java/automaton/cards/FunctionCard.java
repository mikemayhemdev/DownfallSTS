package automaton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FunctionCard extends AbstractBronzeCard {
    public final static String ID = makeID("FunctionCard");

    public FunctionCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    @Override
    public void upp() {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        //No Stack Overflow here.
    }
}
