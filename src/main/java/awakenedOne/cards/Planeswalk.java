package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Planeswalk extends AbstractAwakenedCard {
    public final static String ID = makeID(Planeswalk.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Planeswalk() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        this.cardsToPreview = new VoidCard();
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new GainEnergyAction(magicNumber));
        atb(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }

    public void upp() {
        upgradeBlock(2);
    }
}