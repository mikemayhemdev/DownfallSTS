package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class TestCollectible3 extends AbstractCollectibleCard {
    public final static String ID = makeID(TestCollectible3.class.getSimpleName());
    // intellij stuff skill, self, special, , , , , ,

    public TestCollectible3() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
    }
}