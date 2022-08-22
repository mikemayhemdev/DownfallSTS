package automaton.cards;

import automaton.AutomatonChar;
import automaton.FunctionHelper;
import automaton.cardmods.FullReleaseCardMod;
import automaton.powers.FullReleaseNextFunctionPower;
import automaton.powers.FullReleasePower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullRelease extends AbstractBronzeCard {

    public final static String ID = makeID("FullRelease");

    //stupid intellij stuff attack, enemy, rare

    public FullRelease() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       applyToSelf(new FullReleaseNextFunctionPower());
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}