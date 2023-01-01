package awakenedOne.cards;

import awakenedOne.cardmods.FlyingModifier;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.ToxinWingPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class ToxinWing extends AbstractAwakenedCard {
    public final static String ID = makeID(ToxinWing.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 1, 1

    public ToxinWing() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        CardModifierManager.addModifier(this, new FlyingModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ToxinWingPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}