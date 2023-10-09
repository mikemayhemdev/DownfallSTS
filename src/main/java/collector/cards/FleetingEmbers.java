package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class FleetingEmbers extends AbstractCollectorCard {
    public final static String ID = makeID(FleetingEmbers.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 2, 1

    public FleetingEmbers() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseBlock = 5;
        cardsToPreview = new Ember();
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        makeInHand(new Ember(), magicNumber);
    }

    public void upp() {
        upgradeBlock(3);
    }
}