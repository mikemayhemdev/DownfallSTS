package collector.cards.Collectibles;

import collector.Interfaces.PerpetualCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CurledHorns extends AbstractCollectibleCard implements PerpetualCard {
    public final static String ID = makeID("CurledHorns");

    public CurledHorns() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CollectorCardSource.FRONT);
        FrontBlock = FrontBaseBlock = douBlock = douBaseBlock = block = baseBlock = 4;
        douBaseBlock = baseBlock;
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

    @Override
    public void PerpetualBonus() {}
}