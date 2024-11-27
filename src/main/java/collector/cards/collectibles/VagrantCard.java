package collector.cards.collectibles;

import collector.actions.GainReservesAction;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class VagrantCard extends AbstractCollectibleCard {
    public final static String ID = makeID(VagrantCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 2, 1

    public VagrantCard() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Shame();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainReservesAction(magicNumber));
        makeInHand(new Shame());
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}