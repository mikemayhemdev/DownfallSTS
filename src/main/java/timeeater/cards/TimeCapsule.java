package timeeater.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.powers.HastePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.applyToSelf;

public class TimeCapsule extends AbstractTimeEaterCard implements OnRetrieveCard {
    public final static String ID = makeID("TimeCapsule");
    // intellij stuff skill, none, uncommon, , , , , 2, 1

    public TimeCapsule() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void onRetrieve() {
        superFlash();
        addToBot(new GainEnergyAction(1));
        applyToSelf(new HastePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}