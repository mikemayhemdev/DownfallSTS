package automaton.cards;

import automaton.FunctionHelper;
import automaton.actions.AddToFuncAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Immortalize extends AbstractBronzeCard {

    public final static String ID = makeID("Immortalize");

    //stupid intellij stuff skill, self, basic

    public Immortalize() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(p.hand.group, 1, "Choose.", (cards)-> {
            addToTop(new AddToFuncAction(cards.get(0)));
        }));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (FunctionHelper.held.size() == FunctionHelper.max) {
                    makeInHand(FunctionHelper.makeFunction());
                }
            }
        });
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}