package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CurledHorns extends AbstractCollectibleCard {
    public final static String ID = makeID("CurledHorns");

    public CurledHorns() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        block = baseBlock = 4;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}