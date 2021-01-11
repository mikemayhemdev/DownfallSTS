package automaton.cards;

import automaton.FunctionHelper;
import automaton.actions.RepeatCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Overload extends AbstractBronzeCard {

    public final static String ID = makeID("Overload");

    //stupid intellij stuff skill, self, common

    public Overload() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ORB_LIGHTNING_EVOKE"));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : FunctionHelper.held.group) {
                    addToTop(new RepeatCardAction(q));
                }
            }
        });
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}