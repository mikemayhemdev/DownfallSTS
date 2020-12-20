package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class Debug extends AbstractBronzeCard {

    public final static String ID = makeID("Debug");

    //stupid intellij stuff skill, self, uncommon

    public Debug() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard q : FunctionHelper.held.group) {
            if (q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).doSpecialCompileStuff = false;
                if (q.rawDescription.contains(" NL bronze:Compile")) {
                    String[] splitText = q.rawDescription.split(String.format(WITH_DELIMITER, " NL bronze:Compile"));
                    String compileText = splitText[1] + splitText[2];
                    q.rawDescription = q.rawDescription.replaceAll(compileText, "");
                } //TODO: This entire thing is terrible and placeholder. Make it good eventually!
                else if (q.rawDescription.contains("bronze:Compile")) {
                    q.rawDescription = ""; // It's over!! If you only have Compile effects, you're gone!!!!!
                }
            }
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    } // TODO: Change upgrade
}