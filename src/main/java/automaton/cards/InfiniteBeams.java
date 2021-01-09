package automaton.cards;

import automaton.powers.InfiniteBeamsPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InfiniteBeams extends AbstractBronzeCard {

    public final static String ID = makeID("InfiniteBeams");

    //stupid intellij stuff power, self, uncommon

    public InfiniteBeams() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new MinorBeam();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new InfiniteBeamsPower(1, upgraded));
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        AbstractCard q = new MinorBeam();
        q.upgrade();
        cardsToPreview = q;
    }
}