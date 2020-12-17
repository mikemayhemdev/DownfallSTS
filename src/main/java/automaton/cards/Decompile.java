package automaton.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Decompile extends AbstractBronzeCard {

    public final static String ID = makeID("Decompile");

    //stupid intellij stuff skill, self, uncommon

    public Decompile() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, "Choose a Function.", c -> c instanceof FunctionCard, (cards) -> {
            att(new GainEnergyAction(cards.get(0).cost));
            att(new DrawCardAction(cards.get(0).cost));
            att(new ExhaustSpecificCardAction(cards.get(0), p.hand));
        }));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}