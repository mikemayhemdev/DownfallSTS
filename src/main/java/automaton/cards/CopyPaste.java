package automaton.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CopyPaste extends AbstractBronzeCard {

    public final static String ID = makeID("CopyPaste");

    //stupid intellij stuff skill, self, rare

    public CopyPaste() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, "Choose a Function.", c->c instanceof FunctionCard, (cards) -> {
            att(new MakeTempCardInHandAction(cards.get(0).makeStatEquivalentCopy(), true));
        }));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}