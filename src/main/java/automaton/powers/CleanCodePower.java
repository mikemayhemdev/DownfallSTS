package automaton.powers;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class CleanCodePower extends AbstractAutomatonPower implements PreCardCompileEffectsPower {
    public static final String NAME = "CleanCode";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CleanCodePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receivePreCardCompileEffects(boolean forGameplay) {
        if (forGameplay) {
            flash();
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        for (AbstractCard q : FunctionHelper.held.group) {
            if (q.hasTag(AutomatonMod.BAD_COMPILE) && q instanceof AbstractBronzeCard) {
                if (q.rawDescription.contains(" NL bronze:Compile")) {
                    String[] splitText = q.rawDescription.split(String.format(WITH_DELIMITER, " NL bronze:Compile"));
                    String compileText = splitText[1] + splitText[2];
                    q.rawDescription = q.rawDescription.replaceAll(compileText, "");
                } //TODO: This entire thing is terrible and placeholder. Make it good eventually!
                else if (q.rawDescription.contains("bronze:Compile")) { // This specifically triggers for cards that only have Compile effects.
                    q.rawDescription = ""; // It's over!! If you only have Compile effects, you're gone!!!!!
                }
                q.initializeDescription();
                ((AbstractBronzeCard) q).doSpecialCompileStuff = false;
            }
        }
    }
}
