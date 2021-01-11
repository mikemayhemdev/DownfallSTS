package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class Debug extends AbstractBronzeCard {

    public final static String ID = makeID("Debug");

    //stupid intellij stuff skill, self, uncommon

    public Debug() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard q : FunctionHelper.held.group) {
            if (q.hasTag(AutomatonMod.BAD_COMPILE) && q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).turnOffCompileStuff();
            }
        }
        FunctionHelper.genPreview();
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}