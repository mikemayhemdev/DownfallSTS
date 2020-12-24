package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Overload extends AbstractBronzeCard {

    public final static String ID = makeID("Overload");

    //stupid intellij stuff skill, self, common

    public Overload() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        thisEncodes();
        tags.add(AutomatonMod.MODIFIES_OUTPUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(2));
    }

    @Override
    public boolean onOutput() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(FunctionHelper.makeFunction(true), 1));
        return false;
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}