package collector.cards.Collectibles;

import collector.powers.ChaoticFlux;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;

public class DefectCore extends AbstractCollectibleCard {
    public final static String ID = makeID("DefectCore");

    public DefectCore() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChannelAction(new Plasma()));
        applyToSelf(new ChaoticFlux(p,magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
