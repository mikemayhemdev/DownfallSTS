package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static org.apache.commons.lang3.math.NumberUtils.min;

public class Assembly extends AbstractBronzeCard {

    public final static String ID = makeID("Assembly");

    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 3;

    public Assembly() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> toppers = new ArrayList<>();
        for (int x = 0; x < Math.min(magicNumber, p.drawPile.size()); x++) {
            toppers.add(p.drawPile.group.get(x));
        }
        atb(new SelectCardsAction(toppers, toppers.size(), "Choose any cards with Encode to Encode.", true, c->c.hasTag(AutomatonMod.ENCODES), (cards) -> { //TODO: Localize
            cards.forEach(c->addToTop(new AddToFuncAction(c, p.drawPile)));
        }));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}