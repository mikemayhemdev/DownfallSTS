package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.TemporalBarrierPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class TemporalBarrier extends AbstractTimeEaterCard {
    public final static String ID = makeID("TemporalBarrier");
    // intellij stuff skill, self, common, , , 7, 2, 2, 1

    public TemporalBarrier() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new TemporalBarrierPower(magicNumber));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}