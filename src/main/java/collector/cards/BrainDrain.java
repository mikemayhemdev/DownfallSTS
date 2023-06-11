package collector.cards;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class BrainDrain extends AbstractCollectorCard {
    public final static String ID = makeID(BrainDrain.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public BrainDrain() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = CardLibrary.getCopy(CollectorCollection.collectionPool.get(m.id));
        q.setCostForTurn(0);
        makeInHand(q);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}