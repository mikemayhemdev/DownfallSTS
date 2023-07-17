package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class SpikeSlimeCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SpikeSlimeCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 15, 5, , , , 

    public SpikeSlimeCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new FlameBarrierPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(2);
    }
}