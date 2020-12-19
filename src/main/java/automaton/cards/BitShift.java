package automaton.cards;

import automaton.FunctionHelper;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BitShift extends AbstractBronzeCard {

    public final static String ID = makeID("BitShift");

    //stupid intellij stuff skill, self, common

    public BitShift() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = magicNumber = 1;
        //TODO - Crashes after choosing a card
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(FunctionHelper.held.group, 1, "Choose.", (cards) -> {
            att(new ReduceCostForTurnAction(cards.get(0), magicNumber));
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    FunctionHelper.held.removeCard(cards.get(0));
                    p.hand.addToTop(cards.get(0));
                }
            });
        }));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}