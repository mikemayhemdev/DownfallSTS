package collector.cards.Collectibles;

import collector.Interfaces.PerpetualCard;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.PlayerCurlUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LouseSegment extends AbstractCollectibleCard implements PerpetualCard {
    public final static String ID = makeID("LouseSegment");

    public LouseSegment() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, AbstractCollectorCard.CollectorCardSource.BOTH);
        baseDamage = 1;
        block = baseBlock = 10;
        baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToBack(new PlayerCurlUp(p,block));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
    }

    @Override
    public void PerpetualBonus() {

    }
}