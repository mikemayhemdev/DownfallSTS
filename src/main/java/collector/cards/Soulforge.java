package collector.cards;

import collector.powers.SoulforgePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Soulforge extends AbstractCollectorCard {
    public final static String ID = makeID(Soulforge.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public Soulforge() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new Ember();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SoulforgePower());
    }

    public void upp() {
        isInnate = true;
        uDesc();
    }
}