package automaton.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FindAndReplace extends AbstractBronzeCard {

    public final static String ID = makeID("FindAndReplace");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 1;

    public FindAndReplace() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard gotFunc = null;
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof FunctionCard) {
                gotFunc = c;
            }
        }
        if (gotFunc != null) {
            p.drawPile.moveToDeck(gotFunc, false);
            atb(new ReduceCostForTurnAction(gotFunc, 1));
            atb(new DrawCardAction(1));
        }
        shuffleIn(new Dazed());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean hasFunc = false;
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof FunctionCard) {
                hasFunc = true;
            }
        }
        if (!hasFunc) {
            cantUseMessage = "ERR: NO_FUNCTION"; //TODO: Localize
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}