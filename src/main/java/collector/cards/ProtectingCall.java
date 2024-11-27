package collector.cards;

import collector.powers.TorchHeadPower;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class ProtectingCall extends AbstractCollectorCard {
    public final static String ID = makeID(ProtectingCall.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 6, 2

    public ProtectingCall() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
        baseSecondMagic = secondMagic = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddTemporaryHPAction(p, p, magicNumber));
        applyToSelf(new TorchHeadPower(2, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }
}