package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrudeShield extends AbstractCollectibleCard {
    public final static String ID = makeID("CrudeShield");

    public CrudeShield() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        block = baseBlock = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
