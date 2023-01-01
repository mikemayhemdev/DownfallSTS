package awakenedOne.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.makeInHand;

public class Beacon extends AbstractAwakenedCard implements StartupCard {
    public final static String ID = makeID(Beacon.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public Beacon() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new Miracle();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upp() {
        isEthereal = true;
    }

    @Override
    public boolean atBattleStartPreDraw() {
        makeInHand(new Miracle());
        return true;
    }
}