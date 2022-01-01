package collector.cards.CollectorCards.Powers;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SelfDestruct extends AbstractCollectorCard {
    public final static String ID = makeID("SelfDestruct");

    public SelfDestruct() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE, CollectorCardSource.TORCH_ONLY);
        baseMagicNumber = 25;
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean result = super.canUse(p, m);
        if (result && CollectorChar.getLivingTorchHead() == null) {
            return false;
        }
        return result;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(CollectorChar.torch,CollectorChar.torch,new collector.powers.SelfDestruct(CollectorChar.torch)));
    }

    @Override
    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
    }
}