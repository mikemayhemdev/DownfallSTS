package collector.cards;

import collector.powers.DarkLordFormPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class DarkLordForm extends AbstractCollectorCard {
    public final static String ID = makeID(DarkLordForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public DarkLordForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        cardsToPreview = new YouAreMine();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DarkLordFormPower());
    }

    public void upp() {
        isEthereal = false;
        uDesc();
    }
}