package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class FleetingEmbers extends AbstractCollectorCard {
    public final static String ID = makeID(FleetingEmbers.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 2, 1

    public FleetingEmbers() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Ember();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(new Ember(), magicNumber);
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}