package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.BurningStudy;
import awakenedOne.cards.tokens.spells.Cryostasis;
import awakenedOne.cards.tokens.spells.Darkleech;
import awakenedOne.cards.tokens.spells.Thunderbolt;
import awakenedOne.powers.EmpressPower;
import awakenedOne.powers.InResponsePower;
import awakenedOne.powers.SongOfSorrowPower;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class SongOfSorrow extends AbstractAwakenedCard {
    public final static String ID = makeID(SongOfSorrow.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SongOfSorrow() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SongOfSorrowPower(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}