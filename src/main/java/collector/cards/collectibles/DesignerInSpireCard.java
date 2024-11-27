package collector.cards.collectibles;

import collector.powers.TorchHeadPower;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class DesignerInSpireCard extends AbstractCollectibleCard {
    public final static String ID = makeID(DesignerInSpireCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 6, 1

    public DesignerInSpireCard() {
        super(ID, 4, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AddTemporaryHPAction(p, p, magicNumber));
        applyToSelf(new TorchHeadPower(4, 1));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}