package collector.cards;

import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class OakbrimKindling extends AbstractCollectorCard {
    public final static String ID = makeID(OakbrimKindling.class.getSimpleName());
    // intellij stuff skill, none, uncommon, , , , , 3, 2

    public OakbrimKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 3;
        tags.add(expansionContentMod.UNPLAYABLE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        atb(new DrawCardAction(magicNumber));
        if (upgraded)
            applyToSelf(new NextTurnReservePower(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void upp() {

        uDesc();
    }
}