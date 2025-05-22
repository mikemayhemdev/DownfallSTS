package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.BurningStudy;
import awakenedOne.cards.tokens.spells.Cryostasis;
import awakenedOne.cards.tokens.spells.Darkleech;
import awakenedOne.cards.tokens.spells.Thunderbolt;
import awakenedOne.powers.EmpressPower;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelfTop;
import static awakenedOne.util.Wiz.atb;

public class TheEmpress extends AbstractAwakenedCard {
    public final static String ID = makeID(TheEmpress.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public TheEmpress() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            applyToSelfTop(new EmpressPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}