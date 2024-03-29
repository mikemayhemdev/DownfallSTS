package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Batch extends AbstractBronzeCard {

    public final static String ID = makeID("Batch");

    //stupid intellij stuff skill, self, common

    public Batch() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Batch.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                FunctionHelper.output();
            }
        });
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}