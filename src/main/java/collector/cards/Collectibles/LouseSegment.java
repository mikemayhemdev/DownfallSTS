package collector.cards.Collectibles;

import collector.powers.PlayerCurlUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static collector.CollectorMod.makeID;

public class LouseSegment extends AbstractCollectibleCard {
    public final static String ID = makeID("LouseSegment");

    public LouseSegment() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseDamage = 1;
        block = baseBlock = 8;
        baseMagicNumber = 1;
        this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PlayerCurlUp(p,block));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
    }
}