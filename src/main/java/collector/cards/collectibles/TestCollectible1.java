package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import collector.cards.AbstractCollectorCard;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class TestCollectible1 extends AbstractCollectibleCard {
    public final static String ID = makeID(TestCollectible1.class.getSimpleName());
    // intellij stuff skill, self, special, , , , , , 

    public TestCollectible1() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
    }
}